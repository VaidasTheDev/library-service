
Feature: book management flows

  Scenario: client wants to add a book
    Given the client is providing book details
    And provided book name is 'Sample book'
    And provided book author is 'Vaidas V.'
    And provided book release date is '25-03-2021'
    When the client sends a request to save the book
    Then the client receives status code 201
    And added book should have name 'Sample book'
    And added book should have author 'Vaidas V.'
    And added book should have release date '25-03-2021'

  Scenario Outline: client wants to add a book with insufficient details
    Given the client is providing book details
    And provided book name is '<bookName>'
    And provided book author is '<bookAuthor>'
    And provided book release date is '<bookReleaseDate>'
    When the client sends a request to save the book
    Then the client receives status code 400
    And error should specify field '<errorField>' with message '<errorMessage>'

    Examples:
      | bookName      | bookAuthor  | bookReleaseDate | errorField          | errorMessage             |
      | null          | Vaidas V.   | 25-03-2021      | name                | Name is required         |
      | Spring        | null        | 25-03-2021      | author              | Author is required       |
      | Sample book   | Vaidas V.   | null            | releaseDate         | Release date is required |
