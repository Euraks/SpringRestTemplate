package org.example.service.DTO.mapper;

import org.example.model.entity.Article;
import org.example.model.entity.AuthorEntity;
import org.example.service.DTO.ArticleDTO;
import org.example.service.DTO.AuthorEntityDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class AuthorEntityMapperTest {

    @Mock
    private ArticleMapper articleMapper;

    @InjectMocks
    private AuthorEntityMapperImpl authorEntityMapper;

    @BeforeEach
    public void setup() {
        lenient().when(articleMapper.toDTO(any())).thenReturn(new ArticleDTO());
        lenient().when(articleMapper.toEntity(any())).thenReturn(new Article());
    }

    @Test
    void testToDTO() {
        AuthorEntity authorEntity = new AuthorEntity();
        AuthorEntityDTO result = authorEntityMapper.toDTO(authorEntity);
    }

    @Test
    void testToEntity() {
        AuthorEntityDTO authorEntityDTO = new AuthorEntityDTO();
        AuthorEntity result = authorEntityMapper.toEntity(authorEntityDTO);
    }

    @Test
    void testToDTOList() {
        List<AuthorEntity> authorEntities = new ArrayList<>();
        List<AuthorEntityDTO> result = authorEntityMapper.toDTOList(authorEntities);
    }

    @Test
    void testToEntityList() {
        List<AuthorEntityDTO> authorEntityDTOs = new ArrayList<>();
        List<AuthorEntity> result = authorEntityMapper.toEntityList(authorEntityDTOs);
    }
}
