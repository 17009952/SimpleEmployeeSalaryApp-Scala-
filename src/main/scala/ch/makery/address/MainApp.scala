package ch.makery.address

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader}
import javafx.{scene => jfxs}
import scalafx.collections.{ObservableBuffer}
import ch.makery.address.model.{Person, JuniorEngineer, SeniorEngineer, EntryLevelEngineer}
import ch.makery.address.view.PersonEditDialogController
import scalafx.stage.{ Stage, Modality }

object MainApp extends JFXApp {
  // the data as an observable list of Persons
  val personData = new ObservableBuffer[Person]()

    personData += new JuniorEngineer("Hans Muster", "A1234", "Maybank", "0123456789123","Junior", 4500)
    personData += new EntryLevelEngineer("Ruth Mueller", "B1234", "CIMB", "1234123412","EntryLevel", 3500)
    personData += new EntryLevelEngineer("Heinz Kurz", "C1234", "CIMB", "5678567856","EntryLevel", 3500)
    personData += new SeniorEngineer("Cornelia Meier", "D1234", "Maybank", "230023002300","Senior", 5500)
    personData += new SeniorEngineer("Werner Meyer", "E1234", "CIMB", "6666777767","Senior", 5500)
    personData += new JuniorEngineer("Lydia Kunz", "F1234", "Public", "8888999989","Junior", 4500)
    personData += new EntryLevelEngineer("Anna Best","G1234", "Public", "1111222212","EntryLevel", 3500)
    personData += new EntryLevelEngineer("Stefan Meier","H1234", "Maybank", "000011112222","EntryLevel", 3500)
    personData += new JuniorEngineer("Martin Mueller", "I1234", "Public", "2390239016","Junior", 4500)
  
  // transform path of RootLayout.fxml to URI for resource location.
  val rootResource = getClass.getResourceAsStream("view/RootLayout.fxml")
  // initialize the loader object.
  val loader = new FXMLLoader(null, NoDependencyResolver)
  // Load root layout from fxml file.
  loader.load(rootResource);
  // retrieve the root component BorderPane from the FXML 
  val roots = loader.getRoot[jfxs.layout.BorderPane]
  // initialize stage
  stage = new PrimaryStage {
    title = "EmployeeApp"
    scene = new Scene {
      root = roots
    }
  }
  // actions for display person overview window 
  def showPersonOverview() = {
    val resource = getClass.getResourceAsStream("view/PersonOverview.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  } 
  
   def showPersonEditDialog(person: Person): Boolean = {
    val resource = getClass.getResourceAsStream("view/PersonEditDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[PersonEditDialogController#Controller]

    val dialog = new Stage() {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots2
      }
    }
    control.dialogStage = dialog
    control.person = person
    dialog.showAndWait()
    control.okClicked
  } 
  // call to display PersonOverview when app start
  showPersonOverview()
}