import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.*;

public class Streams {
    private List<Employee> emps = List.of(
            new Employee("Mihael",  "Smith",    243,    43, Position.CHEF),
            new Employee("Jane",    "Smith",    523,    40, Position.MANAGER),
            new Employee("Jury",    "Gagarin",  6423,   26, Position.MANAGER),
            new Employee("Jack",    "London",   5543,   53, Position.WORKER),
            new Employee("Eric",    "Jackson",  2534,   22, Position.WORKER),
            new Employee("Andrew",  "Bosh",     2456,   44, Position.WORKER),
            new Employee("Joe",     "Smith",    723,    30, Position.MANAGER),
            new Employee("Jack",    "Gagarin",  7423,   35, Position.MANAGER),
            new Employee("Jane",    "London",   7543,   42, Position.MANAGER),
            new Employee("Mike",    "Jackson",  7456,   31, Position.WORKER),
            new Employee("Jack",    "Bosh",     123,    54, Position.WORKER),
            new Employee("Mark",    "Smith",    1423,   41, Position.WORKER),
            new Employee("Jane",    "Gagarin",  1543,   28, Position.MANAGER),
            new Employee("Same",    "London",   1534,   52, Position.MANAGER),
            new Employee("Jack",    "Jackson",  1456,   27, Position.WORKER),
            new Employee("Eric",    "Bosh",     356,    32, Position.WORKER)
    );

    private List<Departament> deps = List.of(
            new Departament(1,  0,  "Head"),
            new Departament(2,  1,  "West"),
            new Departament(3,  1,  "East"),
            new Departament(4,  2,  "Germany"),
            new Departament(5,  2,  "France"),
            new Departament(6,  3,  "China"),
            new Departament(7,  3,  "Japan")
    );

    @Test
    public void creation() throws IOException {
        Stream<String> lines = Files.lines(Paths.get("some.txt"));
        Stream<Path> list = Files.list(Paths.get("./"));
        Stream<Path> walk = Files.walk(Paths.get("./"), 3);

        IntStream intStream = IntStream.of(1, 2, 3, 4);
        DoubleStream doubleStream = DoubleStream.of(1.2, 3.4);
        IntStream range = IntStream.range(10, 100); //10 . 99
        IntStream intStream1 = IntStream.rangeClosed(10, 100); //10 . 100

        int[] ints = {1, 2, 3, 4};
        IntStream stream = Arrays.stream(ints);

        Stream<String> stringStream = Stream.of("1", "2");
        Stream<? extends Serializable> stream1 = Stream.of(1, "2", "3");
        Stream<String> build = Stream.<String>builder()
                .add("Mike")
                .add("Joe")
                .build();

        Stream<Employee> stream2 = emps.stream();
        Stream<Employee> employeeStream = emps.parallelStream();

        Stream<Event> generate = Stream.generate(() ->
                new Event(UUID.randomUUID(), LocalDateTime.now(), "")
        );

        Stream<Integer> iterate = Stream.iterate(1950, val -> val + 3);

        Stream<String> concat = Stream.concat(stringStream, build);
    }

    @Test
    public void terminate() {
        //Кол-во
        long count = emps.stream().count();

        emps.forEach(employee -> System.out.println(employee.getAge()));

        //В лист
        emps.stream().collect(Collectors.toList());
        //В массив
        emps.stream().toArray();
        //В мапу
        Map<Long, String> collect = emps.stream().collect(Collectors.toMap(
                Employee::getId,
                employee -> String.format("%s %s", employee.getLastName(), employee.getFirstName())
        ));

        //Инт
        IntStream intStream = IntStream.of(100, 200, 300, 400);
        //Сумма
        System.out.println("123 " + intStream.reduce(Integer::sum).orElse(0));

        //Все департаменты и дети
        System.out.println(deps.stream().reduce(this::reducer));

        //Среднее
        IntStream.of(100, 200, 300, 400).average();
        IntStream.of(100, 200, 300, 400).max();
        IntStream.of(100, 200, 300, 400).min();
        IntStream.of(100, 200, 300, 400).sum();
        //Суммарная статистика
        IntStream.of(100, 200, 300, 400).summaryStatistics();

        //Максимальное по объектам
        emps.stream().max((employee, t1) -> employee.getAge() - t1.getAge());
        emps.stream().max(Comparator.comparingInt(Employee::getAge));

        //Любой
        emps.stream().findAny();
        //Первый
        emps.stream().findFirst();

        //Никто не подходит под условие
        boolean b = emps.stream().noneMatch(employee -> employee.getAge() > 60);//true
        //Все подходят под условие
        boolean b1 = emps.stream().allMatch(employee -> employee.getAge() > 18);//true
        //Один подходит под условие
        boolean b2 = emps.stream().anyMatch(employee -> employee.getPosition().equals(Position.CHEF)); //true
    }

    @Test
    public void transform() {
        //Перевод из инт в лонг
        LongStream longStream = IntStream.of(100, 200, 300, 400).mapToLong(Long::valueOf);
        //Из мапы инт в объект
        IntStream.of(100, 200, 300, 400).mapToObj(value ->
                new Event(UUID.randomUUID(), LocalDateTime.of(value, 12, 1, 12, 0), "")
        );
        //Уникальные значения
        Stream<String> distinct = Stream.of("1", "2", "3", "2").distinct(); //1, 2, 3

        //Фильр
        Stream<Employee> employeeStream = emps.stream().filter(employee -> employee.getPosition() != Position.CHEF);

        //Пропускаем
        emps.stream().skip(3);
        //Максимальное кол-во
        emps.stream().limit(5);

        //Сортируем + Устанавливаем всем возраст 18 + переводим в мапу
        Stream<String> stringStream = emps.stream()
                .sorted(Comparator.comparingInt(Employee::getAge))
                .peek(employee -> employee.setAge(18))
                .map(employee -> String.format("%s %s", employee.getLastName(), employee.getFirstName()));

        //Берем всех пока возраст меньше 30
        emps.stream().takeWhile(employee -> employee.getAge() > 30).forEach(System.out::println);
        System.out.println();
        //Не берем пока возраст меньше 30
        emps.stream().dropWhile( employee -> employee.getAge() > 30).forEach(System.out::println);

        System.out.println();
        //Подмапа, возваращем 50, 100, 150, 200 и тд
        IntStream.of(100, 200, 300, 400)
                .flatMap(value -> IntStream.of(value - 50, value))
                .forEach(System.out::println);
    }

    @Test
    public void real() {
        //Фильтруем тех, кто младше 30 и кто воркер + сортировка по фамилии
        Stream<Employee> sorted = emps.stream()
                .filter(employee ->
                        employee.getAge() <= 30 && employee.getPosition() != Position.WORKER
                )
                .sorted(Comparator.comparing(Employee::getLastName));
        print(sorted);

        //Фильтруем тех, кто старше 44 + сортировка по возрасту от большего к меньшему + лимит 4
        Stream<Employee> sorted1 = emps.stream()
                .filter(employee -> employee.getAge() >= 44)
                .sorted(Comparator.comparing(Employee::getAge).reversed())
                .limit(4);

        print(sorted1);

        //Получаем мапу возрастов всех сотрудников и из нее берем статистику(макс, мин, среднее, сумма)
        IntSummaryStatistics intSummaryStatistics = emps.stream()
                .mapToInt(Employee::getAge)
                .summaryStatistics();

        System.out.println(intSummaryStatistics);
    }


    private void print(Stream<Employee> stream) {
        stream
                .map(emp -> String.format(
                        "%4d | %-15s %-10s age %s %s",
                        emp.getId(),
                        emp.getLastName(),
                        emp.getFirstName(),
                        emp.getAge(),
                        emp.getPosition()
                ))
                .forEach(System.out::println);

        System.out.println();
    }

    public Departament reducer(Departament parent, Departament child) {
        if(child.getParent()  == parent.getId()) {
            parent.getChild().add(child);
        } else {
            parent.getChild().forEach(subParent -> reducer(subParent, child));
        }
        return parent;
    }
}
