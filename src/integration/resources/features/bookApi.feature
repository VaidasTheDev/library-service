
Feature: book management flows

  Scenario: client wants to add a book
    Given the client is providing book details
    And provided book name is 'Sample book name'
    And provided book author is 'Sample Author'
    And provided book release date is '01-01-2015'
    When the client sends a request to save the book
    Then the client receives status code 201
    And added book has name 'Sample book name'
    And added book has author 'Sample Author'
    And added book has release date '01-01-2015'
