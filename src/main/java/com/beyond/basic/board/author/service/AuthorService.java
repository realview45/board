package com.beyond.basic.board.author.service;

import com.beyond.basic.board.author.domain.Author;
import com.beyond.basic.board.author.dtos.*;
import com.beyond.basic.board.author.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional//org.spring으로 import!//트랜잭션의 단위는 Service의 메서드가 된다.
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Client s3Client;
    @Value("${aws.s3.bucket}")
    private String bucket;
    @Autowired
    public AuthorService(AuthorRepository authorRepository, PasswordEncoder passwordEncoder, S3Client s3Client) {
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
        this.s3Client = s3Client;
    }

    public void create(AuthorCreateDto dto, MultipartFile profileImage) {
        if(authorRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이메일이 중복입니다.");
        }
        Author author = authorRepository.save(dto.toEntity(passwordEncoder.encode(dto.getPassword()),profileImage.getOriginalFilename()));
//        파일업로드를 위한 저장 객체 구성 파일명유일해야함
        if(profileImage !=null){
            String fileName = "user-"+author.getId()+"-profileimage"+profileImage.getOriginalFilename();
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .contentType(profileImage.getContentType())//image/jpeg, video/mp4, ...
                    .build();

            //aws에 이미지업로드(byte형태로)
            try {
                s3Client.putObject(request, RequestBody.fromBytes(profileImage.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //aws에 이미지url추출
            String imgUrl = s3Client.utilities().getUrl(a->a.bucket(bucket).key(fileName)).toExternalForm();
            author.updateProfileImageUrl(imgUrl);
        }
    }

    public List<AuthorListDto> findAll() {
        return authorRepository.findAll().stream().map(a->AuthorListDto.fromEntity(a)).collect(Collectors.toList());
    }

    public AuthorDetailDto findById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("엔티티(Author)가 없습니다."));
        return AuthorDetailDto.fromEntity(author);
    }

    public void delete(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("엔티티(Author)가 이미 없습니다."));
        authorRepository.delete(author);
    }

    public void updatePw(AuthorUpdatePwDto dto) {
        //로그인 상태에서는 아래줄이 필요없음
        Author author = authorRepository.findByEmail(dto.getEmail()).orElseThrow(()->new EntityNotFoundException("엔티티(Author)가 없습니다."));
        //dirtychecking으로 save필요없이 변경만 해주면된다.
        author.updatePw(dto.getPassword());
    }

    public Author login(AuthorLoginDto dto) {
        boolean loginSuccess = true;
        Optional<Author> author = authorRepository.findByEmail(dto.getEmail());
        if(!author.isPresent()||!passwordEncoder.matches(dto.getPassword(),author.get().getPassword())){
            loginSuccess=false;
        }
        if(!loginSuccess){
            throw new IllegalArgumentException("이메일 또는 비밀번호가 다릅니다.");
        }
        return author.get();
    }
}
