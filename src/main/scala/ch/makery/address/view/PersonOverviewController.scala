package ch.makery.address.view

import ch.makery.address.model.{Person, JuniorEngineer, SeniorEngineer, EntryLevelEngineer}
import ch.makery.address.MainApp
import scalafx.scene.control.{TableView, TableColumn, Label, Alert}
import scalafxml.core.macros.sfxml
import scalafx.beans.property.{StringProperty, ObjectProperty} 
import ch.makery.address.util.DateUtil._
import scalafx.Includes._
import scalafx.event.ActionEvent

@sfxml
class PersonOverviewController(
  
    private val personTable : TableView[Person],
   
    private val nameColumn : TableColumn[Person, String],
  
    private val empIDColumn : TableColumn[Person, String],

    private val nameLabel : Label,
    
    private val empIDLabel : Label,
  
    private val bankLabel : Label,

    private val accountNoLabel : Label,

    private val positionLabel :  Label,

    private val  salaryLabel : Label  
    
    ) {
  // initialize Table View display contents model
  personTable.items = MainApp.personData
  // initialize columns's cell values
  nameColumn.cellValueFactory = {_.value.name}
  empIDColumn.cellValueFactory  = {_.value.position} 
  
  showPersonDetails(None);
  
  personTable.selectionModel().selectedItem.onChange(
      (_, _, newValue) => showPersonDetails(Some(newValue))
  )
  
  private def showPersonDetails (person : Option[Person]) = {
    person match {
      case Some(person) =>
      // Fill the labels with info from the person object.
      nameLabel.text <== person.name
      empIDLabel.text  <== person.empID
      bankLabel.text    <== person.bank
      accountNoLabel.text      <== person.accountNo
      positionLabel.text <== person.position
      salaryLabel.text = person.salary.value.toString
      case None =>
        // Person is null, remove all the text.
      nameLabel.text = ""
      empIDLabel.text  = ""
      bankLabel.text    = ""
      accountNoLabel.text= ""
      positionLabel.text      = ""
      salaryLabel.text  = ""
    }    
  }
  def handleNewPerson(action : ActionEvent) = {
    val person = new Person("","","","","",0)
    val okClicked = MainApp.showPersonEditDialog(person);
        if (okClicked) {
          if (person.position.getValue.toString == "Junior") {
            val p = new JuniorEngineer(person.name.getValue.toString,person.empID.getValue.toString,person.bank.getValue.toString,person.accountNo.getValue.toString,person.position.getValue.toString,person.salary.getValue.toString.toInt)
            MainApp.personData += p
          }
          else if (person.position.getValue.toString == "Senior") {
            val p = new SeniorEngineer(person.name.getValue.toString,person.empID.getValue.toString,person.bank.getValue.toString,person.accountNo.getValue.toString,person.position.getValue.toString,person.salary.getValue.toString.toInt)
            MainApp.personData += p
          }
          else if (person.position.getValue.toString == "EntryLevel") {
            val p = new EntryLevelEngineer(person.name.getValue.toString,person.empID.getValue.toString,person.bank.getValue.toString,person.accountNo.getValue.toString,person.position.getValue.toString,person.salary.getValue.toString.toInt)
            MainApp.personData += p
          }
        }
  }
  def handleEditPerson(action : ActionEvent) = {
    val selectedPerson = personTable.selectionModel().selectedItem.value
    if (selectedPerson != null) {
        val okClicked = MainApp.showPersonEditDialog(selectedPerson)

        if (okClicked) showPersonDetails(Some(selectedPerson))

    } else {
        // Nothing selected.
        val alert = new Alert(Alert.AlertType.Warning){
          initOwner(MainApp.stage)
          title       = "No Selection"
          headerText  = "No Person Selected"
          contentText = "Please select a person in the table."
        }.showAndWait()
    }
  }
  def handleDeletePerson(action : ActionEvent) = {
      val selectedIndex = personTable.selectionModel().selectedIndex.value
      if (selectedIndex >= 0) {
          personTable.items().remove(selectedIndex);
      } 
   }

  def handleBonus (action: ActionEvent) = {
    for (p <- MainApp.personData){
      var sal = (((p.bonus.value) * (p.salary.value)) + p.salary.value).toInt
      var newSalary = ObjectProperty[Int](sal)
      p.salary = newSalary
    }
  }
  
}