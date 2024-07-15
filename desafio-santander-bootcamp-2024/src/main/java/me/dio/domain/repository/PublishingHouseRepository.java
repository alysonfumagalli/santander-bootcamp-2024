package me.dio.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.dio.domain.model.PublishingHouse;

@Repository
public interface PublishingHouseRepository extends JpaRepository<PublishingHouse, Long> {}
