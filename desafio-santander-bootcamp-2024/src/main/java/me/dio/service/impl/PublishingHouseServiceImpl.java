package me.dio.service.impl;

import static java.util.Optional.ofNullable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.dio.domain.model.PublishingHouse;
import me.dio.domain.repository.PublishingHouseRepository;
import me.dio.service.PublishingHouseService;
import me.dio.service.exception.BusinessException;
import me.dio.service.exception.NotFoundException;

@Service
public class PublishingHouseServiceImpl implements PublishingHouseService {
	@Autowired
	private PublishingHouseRepository publishingHouseRepository;
	
	@Transactional(readOnly = true)
	@Override
	public List<PublishingHouse> findAll() {
		return publishingHouseRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public PublishingHouse findById(Long id) {
		return publishingHouseRepository.findById(id).orElseThrow(NotFoundException::new);
	}

	@Transactional
	@Override
	public PublishingHouse create(PublishingHouse publishingHouseToCreate) {
		ofNullable(publishingHouseToCreate).orElseThrow(() -> new BusinessException("Publishing House to create must not be null."));
		return publishingHouseRepository.save(publishingHouseToCreate);
	}

	@Override
	public PublishingHouse update(Long id, PublishingHouse publishingHouseToUpdate) {
		PublishingHouse dbPublishingHouse = findById(id);
		if (!dbPublishingHouse.getId().equals(publishingHouseToUpdate.getId()))
			throw new BusinessException("Update IDs must be the same.");
		
		dbPublishingHouse.setName(publishingHouseToUpdate.getName());
		
		return publishingHouseRepository.save(dbPublishingHouse);
	}

	@Override
	public void delete(Long id) {
		PublishingHouse dbPublishingHouse = findById(id);
		publishingHouseRepository.delete(dbPublishingHouse);
	}

}
