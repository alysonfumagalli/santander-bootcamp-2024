# Santander Bootcamp 2024 Java - Backend
Repositório dedicado à solução do desafio final do bootcamp "Santander 2024 - Backend com Java" proposto na seção "Ganhando Produtividade no Java com Spring Framework".

## Diagrama de Classes (Domínio da API - Sistema de Gerenciamento de Biblioteca)
```mermaid
classDiagram
    class Book {
        - Long id
        - String title
        - String isbn
        - LocalDate publishedDate
        - Author author
        - PublishingHouse publishingHouse
    }

    class Author {
        - Long id
        - String name
        - LocalDate birthDate
    }

    class PublishingHouse {
        - Long id
        - String name
        - String email
        - String phone
        - LocalDate foundationDate
    }

    Book "n" --> "1" Author
    Book "n" --> "1" PublishingHouse
```
