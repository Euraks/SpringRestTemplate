package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.entity.Tag;
import org.example.repository.TagEntityRepository;
import org.example.service.DTO.TagDTO;
import org.example.service.DTO.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class TagEntityService {

    private final TagEntityRepository tagRepository;

    private final TagMapper tagMapper;

    @Autowired
    public TagEntityService(TagEntityRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Transactional
    public TagDTO getTagById(UUID id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        return tagMapper.toDTO(tag);
    }

    @Transactional
    public List<TagDTO> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(tagMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public TagDTO saveOrUpdateTag(TagDTO dto) {
        Optional<Tag> existingTagOpt = tagRepository.findById(dto.getUuid());

        if (existingTagOpt.isPresent()) {
            Tag existingTag = existingTagOpt.get();
            Tag updatedTag = tagMapper.toEntity(dto);
            existingTag.setTagName(updatedTag.getTagName());
            existingTag.setBookEntities(updatedTag.getBookEntities());
            return tagMapper.toDTO(tagRepository.save(existingTag));
        } else {
            Tag newTag = tagMapper.toEntity(dto);
            return tagMapper.toDTO(tagRepository.save(newTag));
        }
    }

    @Transactional
    public void deleteTag(UUID id) {
        tagRepository.deleteById(id);
    }
}
