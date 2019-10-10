import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

    //declaring all elements and variables
    private Stage _window;
    private Button _diceRoller = new Button("Roll 'em");
    private Button _pointsAccumulator = new Button("Get Points");

    private GridPane _grid = new GridPane();
    private double _hGap = 10;
    private double _vGap = 8;
    private Label[] _diceLabels = new Label[5];
    private Label[] _rollResultLabels = new Label[5];

    private byte _rollCounter = 0;
    private byte _maxRollsPerTurn = 3;
    private Label _rollCountLabel;
    private Label _rollsLabel = new Label("Rolls: ");

    private byte _turnCounter = 1;
    private byte _maxTurnsPerGame = 13;
    private Label _turnCountLabel;
    private Label _turnsLabel = new Label("Turn: ");

    //radiobuttons in array
    private RadioButton[] _numberHolders = new RadioButton[5];
    private int _checkCounter = 0;

    //dropdown and label for categories
    private Label _categoryLabel = new Label("choose category");
    private ChoiceBox<String> _categoryPicker = new ChoiceBox<>();

    //private ArrayList<Integer> _chosenNumbers = new ArrayList<>();
    private HashMap<Integer, Integer> _chosenNumbers = new HashMap<>();

    private boolean _pointsGiven = false;

    public static void main (String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primeStage) throws Exception {
        //initializing everything
        _window = primeStage;
        _window.setTitle("Yahtzee");

        initializePlayfield();

        //lambda expression button handler for rolling dice
        _diceRoller.setOnAction(e -> {
            handleRoll();
        });

        //lambda expression button handler for getting points of current turn
        _pointsAccumulator.setOnAction(e -> {
            getTurnPoints();
        });

        //for loop with lambda expression for key value pairing of chosen numbers(radiobuttons)
        for (int i = 0; i < _numberHolders.length; i++){
            int finalI = i;
            _numberHolders[i].selectedProperty().addListener((observableValue, aBoolean, t1) -> {
                if (!aBoolean){
                    addChosenNumber(finalI, Integer.parseInt(_rollResultLabels[finalI].getText()));
                }else {
                    removeChosenNumber(finalI);
                }
            });
        }

        Scene scene = new Scene(_grid,500, 300);

        _window.setScene(scene);
        _window.show();
    }

    private void handleRoll(){
        for (int rbs = 0; rbs < _numberHolders.length; rbs++){
            if (_numberHolders[rbs].isSelected() && _rollCounter == 0){
                AlertRaiser.displayAlert("Error 01", "Uncheck selection(s) please");
                return;
            }
        }

        if (_rollCounter == 3 && !_pointsGiven && _categoryPicker.getSelectionModel().isEmpty()){
            AlertRaiser.displayAlert("Error 02", "Please choose a category and/or add points");
            return;
        }

        setRollTurnCount();
        setDiceRollResults();
    }

    private void setRollTurnCount(){
        _rollCounter++;
        if (_rollCounter > _maxRollsPerTurn ){
            _turnCounter++;
            _rollCounter = 0;
            _turnCountLabel.setText(Integer.toString(_turnCounter));
        }
        _rollCountLabel.setText(Integer.toString(_rollCounter));
    }

    private void setDiceRollResults(){
        for (int i = 0; i < _rollResultLabels.length; i++){
            if (!(_rollCounter == 0)){
                if (!_numberHolders[i].isSelected())
                    _rollResultLabels[i].setText(Integer.toString(DiceThrower.throwDice()));
            } else {
                _rollResultLabels[i].setText("0");
            }
        }
    }

    private void initializePlayfield(){
        //gridpane
        _grid.setPadding(new Insets(10,10,10,10));
        _grid.setVgap(_vGap);
        _grid.setHgap(_hGap);

        //dice lables
        for (int dl = 0; dl < _diceLabels.length; dl++){
            _diceLabels[dl] = new Label("Dice: " + (dl + 1));
            GridPane.setConstraints(_diceLabels[dl], 3 + dl,0);
            _grid.getChildren().add(_diceLabels[dl]);
        }

        //labels for rollResults
        for (int rr = 0; rr < _rollResultLabels.length; rr++){
            _rollResultLabels[rr] = new Label("0");
            GridPane.setConstraints(_rollResultLabels[rr], 3 + rr, 1);
            _grid.getChildren().add(_rollResultLabels[rr]);
        }
        //labels for roll and turn Counts
        _rollCountLabel = new Label(Integer.toString(_rollCounter));
        _turnCountLabel = new Label(Integer.toString(_turnCounter));

        //radio buttons init
        for (int rb = 0; rb < _numberHolders.length; rb++){
            _numberHolders[rb] = new RadioButton();
            GridPane.setConstraints(_numberHolders[rb], 3 + rb, 2);
            _grid.getChildren().add(_numberHolders[rb]);
        }

        //adding categories
        _categoryPicker.getItems().addAll(
                "Aces",
                "Twos",
                "Threes",
                "Fours",
                "Fives",
                "Sixes",
                "Three of a kind",
                "Four of a kind",
                "Full house",
                "Small straight",
                "Large straight",
                "Yahtzee",
                "Chance"
        );

        GridPane.setConstraints(_diceRoller, 8, 1);
        GridPane.setConstraints(_rollsLabel, 0, 1);;
        GridPane.setConstraints(_turnsLabel, 0, 2);
        GridPane.setConstraints(_rollCountLabel, 1,1);
        GridPane.setConstraints(_turnCountLabel, 1, 2);
        GridPane.setConstraints(_categoryLabel, 8, 2);
        GridPane.setConstraints(_categoryPicker, 8, 3);
        GridPane.setConstraints(_pointsAccumulator, 8, 4);

        _grid.getChildren().addAll(_diceRoller, _rollsLabel, _turnsLabel, _rollCountLabel, _turnCountLabel,_categoryLabel, _categoryPicker, _pointsAccumulator);
    }

    private void getTurnPoints(){
        if (!_categoryPicker.getSelectionModel().isEmpty()){
            String chosenCategory = _categoryPicker.getSelectionModel().getSelectedItem();
            PointAuthorizer.authorizePoints(chosenCategory, _chosenNumbers);
        } else {
            AlertRaiser.displayAlert("Error 03", "Please choose a category");
        }
    }

    private void addChosenNumber(int key, int value){
        _chosenNumbers.put(key, value);
    }

    private void removeChosenNumber(int key){
        _chosenNumbers.remove(key);
    }
}
