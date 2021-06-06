package ru.alexksysx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.alexksysx.dao.*;
import ru.alexksysx.objects.*;
import ru.alexksysx.simplefx.SimpleFXTable;
import ru.alexksysx.simplefx.SimpleFxPagination;

public class AdminController {
    private JdbcTemplate jdbcTemplate = Main.getJdbcTemplate();
    private PriceDao priceDao;
    private PointsDao pointsDao;
    private ModelsDao modelsDao;
    private BusesDao busesDao;
    private RouteDao routeDao;
    private TripDao tripDao;

    // Вкладка с ценами
    @FXML
    private TableView<KmPrice> priceTable;
    @FXML
    private TableColumn<KmPrice, Long> classColumn;
    @FXML
    private TableColumn<KmPrice, Double> priceColumn;
    @FXML
    private TextField classInput;
    @FXML
    private TextField priceInput;
    private SimpleFXTable<KmPrice> simplePriceTable;

    // Вкладка с пунктами
    @FXML
    private TableView<Point> pointsTable;
    @FXML
    private TableColumn<Point, String> pointsNameColumn;
    @FXML
    private TableColumn<Point, Long> pointsDistanceColumn;
    @FXML
    private TextField pointsNameInput;
    @FXML
    private TextField pointsDistanceInput;
    private SimpleFXTable<Point> simplePointTable;

    // Вкладка с моделями автобусов
    @FXML
    private ChoiceBox<KmPrice> modelsClassChoice;
    @FXML
    private TableView<Model> modelsTable;
    @FXML
    private TableColumn<Model, String> modelNameColumn;
    @FXML
    private TableColumn<Model, Integer> modelPlacesColumn;
    @FXML
    private TableColumn<Model, Long> modelClassColumn;
    @FXML
    private TableView<Bus> busesTable;
    @FXML
    private TableColumn<Bus, String> busNumberColumn;
    @FXML
    private TextField modelNameInput;
    @FXML
    private TextField modelPlacesInput;
    @FXML
    private TextField busNumberInput;
    private SimpleFXTable<Model> simpleModelsTable;
    private SimpleFXTable<Bus> simpleBusTable;

    // Маршруты и рейсы
    @FXML
    private Pagination routesPagination;
    @FXML
    private TextField routesNameInput;
    private SimpleFxPagination<Route> simpleFxRoutesPagination;



    @FXML
    public void initialize() {
        priceDao = new PriceDao(jdbcTemplate);
        pointsDao = new PointsDao(jdbcTemplate);
        modelsDao = new ModelsDao(jdbcTemplate);
        busesDao = new BusesDao(jdbcTemplate);
        routeDao = new RouteDao(jdbcTemplate);
        tripDao = new TripDao(jdbcTemplate);

        simplePriceTable = new SimpleFXTable.Builder<KmPrice>(priceTable)
                .withData(priceDao.getAll())
                .withLongColumn(classColumn, "modelClass")
                .withDoubleColumn(priceColumn, "price")
                .isEditable(true)
                .build();
        simplePointTable = new SimpleFXTable.Builder<Point>(pointsTable)
                .withData(pointsDao.getAll())
                .withStringColumn(pointsNameColumn, "namePoint")
                .withLongColumn(pointsDistanceColumn, "distance")
                .isEditable(true)
                .build();
        simpleModelsTable = new SimpleFXTable.Builder<Model>(modelsTable)
                .withData(modelsDao.getAll())
                .withStringColumn(modelNameColumn, "nameModel")
                .withIntegerColumn(modelPlacesColumn, "places")
                .withLongColumn(modelClassColumn, "modelClass")
                .isEditable(true)
                .build();
        simpleBusTable = new SimpleFXTable.Builder<Bus>(busesTable)
                .withStringColumn(busNumberColumn, "busNumber")
                .build();
        modelsClassChoice.setItems(simplePriceTable.getObservableList());
        modelsClassChoice.getSelectionModel().select(0);

        // Заполнение pagination
        simpleFxRoutesPagination = new SimpleFxPagination<>(routesPagination, routeDao.getAll());

        // Заполнение названия маршрута
        if (simpleFxRoutesPagination.getElement() != null)
            routesNameInput.setText(simpleFxRoutesPagination.getElement().getNameRoute());

        // обновление названия и рейсов при смене маршрута

        simpleFxRoutesPagination.currentPageIndexProperty().addListener(((observableValue, number, t1) -> {
            routesNameInput.setText(simpleFxRoutesPagination.getElement().getNameRoute());
            // Здесь добавляются команды, выполняемые при смене страницы
        }));
    }

    // Методы вкладки "Цены"

    public void addPriceRow(ActionEvent actionEvent) {
        Long classId = Long.parseLong(classInput.getText());
        Double price = Double.parseDouble(priceInput.getText());
        KmPrice one = priceDao.createOne(new KmPrice(classId, price));
        if (one != null) {
            simplePriceTable.addRow(one);
            classInput.clear();
            priceInput.clear();
        }
    }

    public void deletePriceRow(ActionEvent actionEvent) {
        KmPrice price = simplePriceTable.getSelectedRow();
        Integer selectedRowIndex = simplePriceTable.getSelectedRowIndex();
        if (price != null) {
            boolean deleted = priceDao.deleteOneById(price.getModelClass());
            if (deleted)
                simplePriceTable.removeRowByIndex(selectedRowIndex);
        }
    }

    public void editPriceRow(TableColumn.CellEditEvent<KmPrice, Double> kmPriceDoubleCellEditEvent) {
        KmPrice price = simplePriceTable.getSelectedRow();
        price.setPrice(kmPriceDoubleCellEditEvent.getNewValue());
        if (!priceDao.updateOne(price))
            price.setPrice(kmPriceDoubleCellEditEvent.getOldValue());
    }

    // Методы вкладки "Пункты"

    public void pointsAddRow(ActionEvent actionEvent) {
        String name = pointsNameInput.getText();
        Long distance = Long.parseLong(pointsDistanceInput.getText());
        Point point = pointsDao.createOne(new Point(name, distance));
        if (point.getCodPoint() != null) {
            simplePointTable.addRow(point);
            pointsNameInput.clear();
            pointsDistanceInput.clear();
        }
    }

    public void pointsDeleteRow(ActionEvent actionEvent) {
        Point point = simplePointTable.getSelectedRow();
        Integer selectedRowIndex = simplePointTable.getSelectedRowIndex();
        if (point != null) {
            boolean deleted = pointsDao.deleteOneById(point.getCodPoint());
            if (deleted)
                simplePointTable.removeRowByIndex(selectedRowIndex);
        }
    }

    public void editPointsName(TableColumn.CellEditEvent<Point, String> pointStringCellEditEvent) {
        Point point = simplePointTable.getSelectedRow();
        point.setNamePoint(pointStringCellEditEvent.getNewValue());
        if(!pointsDao.updateOne(point))
            point.setNamePoint(pointStringCellEditEvent.getOldValue());
    }

    public void editPointsDistance(TableColumn.CellEditEvent<Point, Long> pointLongCellEditEvent) {
        Point point = simplePointTable.getSelectedRow();
        point.setDistance(pointLongCellEditEvent.getNewValue());
        if(!pointsDao.updateOne(point))
            point.setDistance(pointLongCellEditEvent.getOldValue());
    }

    // Методы вкладки модели и автобусы
    // TODO добавить редактирование
    // TODO добавить редактирование класса через выпадающее меню

    public void selectModel(MouseEvent mouseEvent) {
        Model model = simpleModelsTable.getSelectedRow();
        if (model == null)
            return;
        simpleBusTable.cleanTable();
        simpleBusTable.setElementList(busesDao.getBusesByModel(model.getCodModel()));
    }

    public void addModelRow(ActionEvent actionEvent) {
        String name = modelNameInput.getText();
        Integer places = Integer.parseInt(modelPlacesInput.getText());
        Long modelClass = modelsClassChoice.getSelectionModel().getSelectedItem().getModelClass();
        Model one = modelsDao.createOne(new Model(name, places, modelClass));
        if (one.getCodModel() != null) {
            simpleModelsTable.addRow(one);
            modelNameInput.clear();
            modelPlacesInput.clear();
        }
    }

    public void deleteModelRow(ActionEvent actionEvent) {
        Model model = simpleModelsTable.getSelectedRow();
        Integer selectedRowIndex = simpleModelsTable.getSelectedRowIndex();
        if (model != null)
            if (modelsDao.deleteOneById(model.getCodModel()))
                simpleModelsTable.removeRowByIndex(selectedRowIndex);
    }

    public void addBusRow(ActionEvent actionEvent) {
        String busNumber = busNumberInput.getText();
        Model model = simpleModelsTable.getSelectedRow();
        if (model != null) {
            Bus bus = busesDao.createOne(new Bus(model.getCodModel(), busNumber));
            if (bus.getCodBus() != null) {
                simpleBusTable.addRow(bus);
                busNumberInput.clear();
            }
        }
    }

    public void deleteBusRow(ActionEvent actionEvent) {
        Bus bus = simpleBusTable.getSelectedRow();
        Integer selectedRowIndex = simpleBusTable.getSelectedRowIndex();
        if(bus != null)
            if(busesDao.deleteOneById(bus.getCodBus()))
                simpleBusTable.removeRowByIndex(selectedRowIndex);
    }

    // Маршруты и рейсы


}
