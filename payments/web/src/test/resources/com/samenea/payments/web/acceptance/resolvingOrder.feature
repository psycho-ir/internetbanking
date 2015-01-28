Feature: resolving order
  As an admin,
  I want to resolve unresolved orders manually.

Scenario: no unresolved orders
  Given there is no unresolved orders
  When admin gets unresolved orders page
  Then the page doesn't show any order to resolve

