package org.example.service;

import org.example.model.entity.Article;
import org.example.model.entity.AuthorEntity;
import org.example.repository.AuthorEntityRepository;
import org.example.service.DTO.AuthorEntityDTO;
import org.example.service.DTO.mapper.AuthorEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthorEntityService {

    private final AuthorEntityRepository repository;
    private final AuthorEntityMapper mapper;


    @Autowired
    public AuthorEntityService(AuthorEntityRepository repository, AuthorEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public AuthorEntityDTO getAuthorById(UUID id) {
        AuthorEntity author = repository.findByIdWithArticles( id )
                .orElseThrow( () -> new RuntimeException( "Author not found" ) );
        return mapper.toDTO( author );
    }

    @Transactional
    public List<AuthorEntityDTO> getAllAuthors() {
        List<AuthorEntity> authors = repository.findAllWithArticles();
        return authors.stream().map( mapper::toDTO ).collect( Collectors.toList() );
    }

    @Transactional
    public AuthorEntityDTO saveAuthor(AuthorEntityDTO dto) {
        AuthorEntity updatingAuthor = mapper.toEntity( dto );

        if (updatingAuthor.getUuid() != null && repository.existsById( updatingAuthor.getUuid() )) {
            AuthorEntity existingAuthor = repository.findByIdWithArticles( updatingAuthor.getUuid() ).orElse( null );

            existingAuthor.setAuthorName( updatingAuthor.getAuthorName() );

            for (Article updatingArticle : updatingAuthor.getArticleList()) {

                Article existingArticle = existingAuthor.getArticleList().stream()
                        .filter( article -> article.getUuid().equals( updatingArticle.getUuid() ) )
                        .findFirst().orElse( null );

                if (existingArticle != null) {
                    existingArticle.setText( updatingArticle.getText() );
                } else {
                    updatingArticle.setAuthor( existingAuthor );
                    existingAuthor.getArticleList().add( updatingArticle );
                }
            }


            updatingAuthor = repository.save( existingAuthor );
        } else {
            if (updatingAuthor.getArticleList() != null) {
                for (Article article : updatingAuthor.getArticleList()) {
                    article.setAuthor( updatingAuthor );
                }
            }
            updatingAuthor = repository.save( updatingAuthor );
        }

        return mapper.toDTO( updatingAuthor );
    }

    @Transactional
    public void deleteAuthor(UUID id) {
        repository.deleteById( id );
    }
}
