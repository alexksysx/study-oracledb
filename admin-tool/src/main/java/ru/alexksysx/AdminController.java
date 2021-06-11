package ru.alexksysx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import ru.alexksysx.dao.*;
import ru.alexksysx.objects.*;
import ru.alexksysx.simplefx.SimpleFXTable;
import ru.alexksysx.simplefx.SimpleFxPagination;

import java.util.Map;

public class AdminController {
    private JdbcTemplate jdbcTemplate = Main.getJdbcTemplate();
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
    private PriceDao priceDao;

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
    private PointsDao pointsDao;

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
    private ModelsDao modelsDao;
    private BusesDao busesDao;

    // Маршруты и рейсы
    @FXML
    private Pagination routesPagination;
    @FXML
    private TextField routesNameInput;
    @FXML
    private TextField routesAddInput;
    @FXML
    private TextField dayOfWeekInput;
    @FXML
    private TextField hoursInput;
    @FXML
    private TextField minutesInput;
    @FXML
    private TextField ticketsInput;
    @FXML
    private ChoiceBox<Point> tripsPointsChoice;
    @FXML
    private ChoiceBox<Bus> busRouteChoice;
    @FXML
    private TableView<Point> routesPointsTable;
    @FXML
    private TableView<Trip> tripsTable;
    @FXML
    private TableColumn<Trip, Integer> weekDayColumn;
    @FXML
    private TableColumn<Trip, Integer> hoursColumn;
    @FXML
    private TableColumn<Trip, Integer> minutesColumn;
    @FXML
    private TableColumn<Trip, Integer> ticketsColumn;
    @FXML
    private TableColumn<Trip, String> tripBusNumberColumn;
    @FXML
    private TableColumn<Point, String> routesPointsColumn;
    private SimpleFxPagination<Route> simpleFxRoutesPagination;
    private SimpleFXTable<Point> simpleRoutePointsTable;
    private SimpleFXTable<Trip> simpleTripsTable;
    private PointsRoutesDao pointsRoutesDao;


    @FXML
    public void initialize() {
        priceDao = new PriceDao(jdbcTemplate);
        simplePriceTable = new SimpleFXTable.Builder<KmPrice>(priceTable)
                .withData(priceDao.getAll())
                .withLongColumn(classColumn, "modelClass")
                .withDoubleColumn(priceColumn, "price")
                .isEditable(true)
                .build();

        pointsDao = new PointsDao(jdbcTemplate);
        simplePointTable = new SimpleFXTable.Builder<Point>(pointsTable)
                .withData(pointsDao.getAll())
                .withStringColumn(pointsNameColumn, "namePoint")
                .withLongColumn(pointsDistanceColumn, "distance")
                .isEditable(true)
                .build();


        modelsDao = new ModelsDao(jdbcTemplate);
        busesDao = new BusesDao(jdbcTemplate);
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


        // Маршруты и рейсы
        routeDao = new RouteDao(jdbcTemplate);
        tripDao = new TripDao(jdbcTemplate);
        pointsRoutesDao = new PointsRoutesDao(jdbcTemplate);
        simpleRoutePointsTable = new SimpleFXTable.Builder<>(routesPointsTable)
                .withStringColumn(routesPointsColumn, "namePoint")
                .build();
        simpleTripsTable = new SimpleFXTable.Builder<>(tripsTable)
                .withIntegerColumn(weekDayColumn, "weekDay")
                .withIntegerColumn(hoursColumn, "hour")
                .withIntegerColumn(minutesColumn, "minute")
                .withIntegerColumn(ticketsColumn, "tickets")
                .withStringColumn(tripBusNumberColumn, "busNumber")
                .build();
        tripsPointsChoice.setItems(simplePointTable.getObservableList());
        tripsPointsChoice.getSelectionModel().select(0);
        updateBusChoiceBox(null);
        // Заполнение pagination
        simpleFxRoutesPagination = new SimpleFxPagination<>(routesPagination, routeDao.getAll());
        // Заполнение названия маршрута
        if (simpleFxRoutesPagination.getElement() != null) {
            routesNameInput.setText(simpleFxRoutesPagination.getElement().getNameRoute());
            // Заполнение пунктов маршрута
            simpleRoutePointsTable.setElementList(
                    pointsRoutesDao.getPointsFromRoute(simpleFxRoutesPagination.getElement().getCodRoute()));
        }

        // обновление названия и рейсов при смене маршрута
        simpleFxRoutesPagination.currentPageIndexProperty().addListener(((observableValue, number, t1) -> {
            Route route = simpleFxRoutesPagination.getElement();
            routesNameInput.setText(route.getNameRoute());
            simpleRoutePointsTable.cleanTable();
            simpleTripsTable.cleanTable();
            simpleRoutePointsTable.setElementList(pointsRoutesDao.getPointsFromRoute(route.getCodRoute()));
            simpleTripsTable.setElementList(tripDao.getTripsByCodRoute(route.getCodRoute()));
            // Здесь добавляются команды, выполняемые при смене страницы
        }));
    }

    // Пример вызова процедуры
    public void testProc() {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withProcedureName("test_pro");
        SqlParameterSource in = new MapSqlParameterSource().addValue("t1", 12).addValue("t2", "test");
        Map<String, Object> out = call.execute(in);
        Integer t = 10;
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
        if (price != null) {
            boolean deleted = priceDao.deleteOneById(price.getModelClass());
            if (deleted)
                simplePriceTable.removeElement(price);
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
        if (point != null) {
            boolean deleted = pointsDao.deleteOneById(point.getCodPoint());
            if (deleted)
                simplePointTable.removeElement(point);
        }
    }

    public void editPointsName(TableColumn.CellEditEvent<Point, String> pointStringCellEditEvent) {
        Point point = simplePointTable.getSelectedRow();
        point.setNamePoint(pointStringCellEditEvent.getNewValue());
        if (!pointsDao.updateOne(point))
            point.setNamePoint(pointStringCellEditEvent.getOldValue());
    }

    public void editPointsDistance(TableColumn.CellEditEvent<Point, Long> pointLongCellEditEvent) {
        Point point = simplePointTable.getSelectedRow();
        point.setDistance(pointLongCellEditEvent.getNewValue());
        if (!pointsDao.updateOne(point))
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
        if (model != null)
            if (modelsDao.deleteOneById(model.getCodModel()))
                simpleModelsTable.removeElement(model);
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
        if (bus != null)
            if (busesDao.deleteOneById(bus.getCodBus()))
                simpleBusTable.removeElement(bus);
    }

    // Маршруты и рейсы
    public void addRouteRow(ActionEvent actionEvent) {
        String routeName = routesAddInput.getText();
        Route route = routeDao.createOne(new Route(routeName));
        if (route.getCodRoute() != null) {
            simpleFxRoutesPagination.addPage(route);
            routesNameInput.setText(route.getNameRoute());
        }
    }

    public void deleteRouteRow(ActionEvent actionEvent) {
        Route route = simpleFxRoutesPagination.getElement();
        if (route == null)
            return;
        if (routeDao.deleteOneById(route.getCodRoute())) {
            simpleFxRoutesPagination.deletePage();
            routesNameInput.setText(simpleFxRoutesPagination.getElement().getNameRoute());
        }
    }

    public void updateRouteName(ActionEvent actionEvent) {
        Route route = simpleFxRoutesPagination.getElement();
        String oldName = route.getNameRoute();
        route.setNameRoute(routesNameInput.getText());
        if (!routeDao.updateOne(route)) {
            route.setNameRoute(oldName);
            routesNameInput.setText(oldName);
        }
    }

    public void updateBusChoiceBox(MouseEvent mouseEvent) {
        busRouteChoice.setItems(FXCollections.observableList(busesDao.getAll()));
    }

    public void addPointToRoute(ActionEvent actionEvent) {
        Point point = tripsPointsChoice.getSelectionModel().getSelectedItem();
        Route route = simpleFxRoutesPagination.getElement();
        if (pointsRoutesDao.addPointToRoute(route.getCodRoute(), point.getCodPoint())) {
            simpleRoutePointsTable.addRow(point);
        }
    }

    public void removePointFromRoute(ActionEvent actionEvent) {
        Point point = simpleRoutePointsTable.getSelectedRow();
        if (point == null)
            return;
        Route route = simpleFxRoutesPagination.getElement();
        if (pointsRoutesDao.removePointFromRoute(route.getCodRoute(), point.getCodPoint()))
            simpleRoutePointsTable.removeElement(point);
    }

    public void addTripRow(ActionEvent actionEvent) {
        Long codRoute = simpleFxRoutesPagination.getElement().getCodRoute();
        Integer weekDay = Integer.parseInt(dayOfWeekInput.getText());
        Integer hours = Integer.parseInt(hoursInput.getText());
        Integer minutes = Integer.parseInt(minutesInput.getText());
        Bus bus = busRouteChoice.getSelectionModel().getSelectedItem();
        Integer tickets = modelsDao.getOneById(bus.getCodModel()).getPlaces();
        Trip trip = tripDao.createOne(new Trip(weekDay, hours, minutes, codRoute, bus.getCodBus(), tickets));
        if (trip.getCodTrip() != null) {
            trip.setBusNumber(bus.getBusNumber());
            simpleTripsTable.addRow(trip);
            dayOfWeekInput.clear();
            hoursInput.clear();
            minutesInput.clear();
        }
    }

    public void removeTripRow(ActionEvent actionEvent) {
        Trip trip = simpleTripsTable.getSelectedRow();
        if (trip != null)
            if (tripDao.deleteOneById(trip.getCodTrip()))
                simpleTripsTable.removeElement(trip);
    }
}
