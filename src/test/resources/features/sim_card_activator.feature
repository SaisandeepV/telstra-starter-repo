Feature: SIM Card Activation
  As a user
  I want to activate SIM cards
  So that I can use them for my services

  Scenario: Successful SIM card activation
    Given I have an ICCID "1255789453849037777" for record ID 1
    When I send a POST request to "/sim/activate" to activate the SIM
    Then the activation response should indicate success
    And the SIM card record should have active status "true"

  Scenario: Failed SIM card activation
    Given I have an ICCID "8944500102198304826" for record ID 2
    When I send a POST request to "/sim/activate" to activate the SIM
    Then the activation response should indicate failure
    And the SIM card record should have active status "false"
