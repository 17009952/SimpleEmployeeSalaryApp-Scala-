package ch.makery.address.view

import ch.makery.address.model.{Person, JuniorEngineer, SeniorEngineer, EntryLevelEngineer}
import ch.makery.address.MainApp
import scalafx.scene.control.{TextField, TableColumn, Label, Alert}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.Includes._
import scalafx.event.ActionEvent

@sfxml
class PersonEditDialogController (

    private val  nameField : TextField,
    private val   empIDField : TextField,
    private val     bankField : TextField,
    private val positionField : TextField,
    private val       accountNoField : TextField,
    private val   salaryField : TextField

){
  var         dialogStage : Stage  = null
  private var _person     : Person = null
  var         okClicked            = false

  def person = _person
  def person_=(x : Person) {
        _person = x

        nameField.text = _person.name.value
        empIDField.text  = _person.empID.value
        bankField.text    = _person.bank.value
        salaryField.text= _person.salary.value.toString
        accountNoField.text      = _person.accountNo.value
        positionField.text  = _person.position.value
  }

  def handleOk(action :ActionEvent){

     if (isInputValid()) {
        _person.name <== nameField.text
        _person.empID  <== empIDField.text
        _person.bank    <== bankField.text
        _person.accountNo      <== accountNoField.text
        _person.position  <== positionField.text
        _person.salary.value = salaryField.getText().toInt

        okClicked = true;
        dialogStage.close()
    }
  }

  def handleCancel(action :ActionEvent) {
        dialogStage.close();
  }
  def nullChecking (x : String) = x == null || x.length == 0

  def isInputValid() : Boolean = {
    var errorMessage = ""

    if (nullChecking(nameField.text.value))
      errorMessage += "Not valid name!\n"
    if (nullChecking(empIDField.text.value))
      errorMessage += "Not valid employee ID!\n"
    if (nullChecking(bankField.text.value))
      errorMessage += "Not valid bank!\n"
    if (nullChecking(salaryField.text.value))
      errorMessage += "Not valid salary!\n"
    else {
      try {
        Integer.parseInt(salaryField.getText());
      } catch {
          case e : NumberFormatException =>
            errorMessage += "No valid salary (must be an integer)!\n"
      }
    }
    if (nullChecking(accountNoField.text.value))
      errorMessage += "Not valid account number!\n"
    if (nullChecking(positionField.text.value))
      errorMessage += "Not valid position!\n"

    if (errorMessage.length() == 0) {
        return true;
    } else {
        // Show the error message.
        val alert = new Alert(Alert.AlertType.Error){
          initOwner(dialogStage)
          title = "Invalid Fields"
          headerText = "Please correct invalid fields"
          contentText = errorMessage
        }.showAndWait()

        return false;
    }
   }
}