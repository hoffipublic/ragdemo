Feature: BaseController

  Scenario: calling the root path of the application
    Given a running application - BaseController
    When I call to the root path of the application - BaseController
    Then the response should be fine - BaseController
    Then common status 200
