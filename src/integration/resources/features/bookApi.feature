
Feature: book management flows

  Scenario: client wants to add a book
    Given the client is providing book details
    And provided book name is 'Sample book'
    And provided book author is 'Vaidas V.'
    And provided book release date is '25-03-2021'
    When the client sends a request to save the book
    Then the client receives status code 201
    And added book has name 'Sample book'
    And added book has author 'Vaidas V.'
    And added book has release date '25-03-2021'

  Scenario Outline: client wants to add a book but with insufficient details
    Given the client is providing book details
    And provided book name is '<bookName>'
    And provided book author is '<bookAuthor>'
    And provided book release date is '<bookReleaseDate>'
    When the client sends a request to save the book
    Then the client receives status code 201
    And added book has name '<bookName>'
    And added book has author '<bookAuthor>'
    And added book has release date '<bookReleaseDate>'

    Examples:
      | bookName            | bookAuthor  | bookReleaseDate |
      | Spring Chronicles   | null        | 25-03-2021      |
      | Sample book         | Vaidas V.   | null            |
      | null                | Vaidas V.   | 25-03-2021      |
