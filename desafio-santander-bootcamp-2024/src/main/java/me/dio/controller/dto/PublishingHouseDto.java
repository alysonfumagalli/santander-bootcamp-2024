package me.dio.controller.dto;

import java.time.LocalDate;

import me.dio.domain.model.PublishingHouse;

public record PublishingHouseDto(Long id, String name, String email, String phone, LocalDate foundationDate) {

    public PublishingHouseDto(PublishingHouse model) {
        this(model.getId(), model.getName(), model.getEmail(), model.getPhone(), model.getFoundationDate());
    }

    public PublishingHouse toModel() {
        PublishingHouse model = new PublishingHouse();
        model.setId(this.id);
        model.setName(this.name);
        model.setEmail(this.email);
        model.setPhone(this.phone);
        model.setFoundationDate(this.foundationDate);
        return model;
    }
}