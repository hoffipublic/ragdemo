Feature: AuthorController

  Scenario: 001 - AuthorController call gives correct result
    Given a username 'federico' - AuthorController
    And an email 'federico.paparoni@xyz.mail' - AuthorController
    And a bio 'Federico Paparoni wants to write something about Cucumber & Testcontainer' - AuthorController
    When I submit this information to save a new user - AuthorController
    Then I receive a correct response - AuthorController
    Then common status 200

#  Scenario: 002 - Save author without username KO
#    Given an email 'federico.paparoni@xyz.mail'
#    And a bio 'Federico Paparoni wants to write something about Cucumber & Testcontainer'
#    When I submit this information to save a new user
#    Then I receive an error
