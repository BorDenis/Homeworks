package Lesson_6;

import java.beans.IntrospectionException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class Tests {
    public static void main(String[] args) {
        Tests result = new Tests();
//        System.out.println("--- test_1 ---");
//        result.test_1();
//        System.out.println("--- test_2--- ");
//        result.test_2();
//        System.out.println("--- test_3 ---");
//        result.test_3();
//        System.out.println("--- test_4 ---");
//        result.test_4();
//        System.out.println("--- test_5 ---"); FAIL!!!!
//        result.test_5();
//        System.out.println("--- test_6 ---");
//        result.test_6();
//        System.out.println("--- test_7 ---");
//        result.test_7();
//        System.out.println("--- test_8 ---");
//        result.test_8();
        System.out.println("--- test_9 ---");
        result.test_9();



        System.out.println();
    }

    /**
     * Получить List чисел в виде текстовых элементов
     */
    public void test_1() {
        List<Integer> integerList = getIntList();
        System.out.println(integerList.stream().map(String::valueOf).toList());


    }

    /**
     * Отсортировать список по убыванию
     */
    public void test_2() {
        List<Integer> integerList = getIntList();
//        List<Integer> integerListResult = integerList.stream().sorted((item_1, item_2) -> {
//            int result = 0;
//            if (item_1 > item_2) result = -1;
//            else if (item_1 < item_2) result = 1;
//            return result;
//        }).toList();
//        System.out.println(integerListResult);
        System.out.println(integerList.stream().sorted(Comparator.reverseOrder()).toList());
    }

    /**
     * Получить одну строку текста. Каждый элемент должен начинаться с текста "Number - ".
     * Элементы должны разделяться запятой и пробелом.
     * В начале итоговой строки должен быть текст "Number list: "
     * В конце итоговой строки должен быть текст "end of list.".
     */
    public void test_3() {
        List<String> stringList = getStringList();
        String result_2 = stringList.stream().map(e -> "Number - " + e)
                .collect(Collectors.joining(", ", "Number list: ", ". end of list."  ));
        System.out.println(result_2);
    }

    /**
     * Получить мапу со значениями, ключи которых больше трех и меньше девяти
     */
    public void test_4() {
        Map<Integer, String> map = getMap();
        System.out.println(map.entrySet().stream().filter(item -> item.getKey() > 3 && item.getKey() < 9)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    /**
     * Перемешать все элементы в мапе.
     * Пример результата:
     * Элемент 1: ключ - 5, значение "five"
     * Элемент 2: ключ - 7, значение "seven"
     * Элемент 3: ключ - 2, значение "two"
     * и так далее.
     */
    public void test_5() {
        Map<Integer, String> map = getMap();
        Map<Integer, String> mapLinked = new LinkedHashMap<>(map);

        final int[] i = {0};

        map.entrySet().stream().forEach(item -> {
            System.out.format("Элемент %s: ключ - %s, значение \"%s\"\n ", i[0], item.getKey(), item.getValue());
            i[0]++;
        });
        System.out.println(mapLinked);

    }

    /**
     * Установить во всех элементах isDisplayed = true, и оставить в списке только элементы с value NULL.
     */
    public void test_6() {
        List<WebElement> elements = getElements();
        elements.stream().peek(item -> item.setDisplayed(true)).filter(item -> item.getValue()==null)
                .forEach(item -> System.out.format("isDisplayed - %s, value - %s, Type - %s, text - %s,\n",
                        item.isDisplayed(), item.getValue(), item.getType(), item.getText()));
//        elements.stream().peek(item -> item.setDisplayed(true)).filter(item -> item.getValue()==null).toList();

    }

    /**
     * Инвертировать isDisplayed параметр каждого элемента и отсортировать список по типу элемента
     * со следующим приоритетом:
     * 1. TEXT
     * 2. INPUT_FIELD
     * 3. CHECKBOX
     * 4. BUTTON
     * 5. RADIO_BUTTON
     * 6. IMAGE
     */
    public void test_7() {
        List<WebElement> elements = getElements();
        elements.stream().sorted((item_1, item_2) -> {
            List<Object> order = List.of("TEXT", "INPUT_FIELD","CHECKBOX","BUTTON","RADIO_BUTTON","IMAGE");
            int result = 0;
            int orderNum_1 = order.indexOf(String.valueOf(item_1.getType())); //ищем type в order и берем идекс
            int orderNum_2 = order.indexOf(String.valueOf(item_2.getType()));
            if (orderNum_1 < orderNum_2) result = -1;
            else if (orderNum_1 > orderNum_2) result = 1;
            return  result;
        }).forEach(item -> item.setDisplayed(!item.isDisplayed()));
    }

    /**
     * Создать мапу:
     * ключ - текст
     * значение - тип элемента
     */
    public void test_8() {
        List<WebElement> elements = getElements();
        final int[] i = {0};
        Map<String,Object> elementsResult = elements.stream().collect(Collectors.toMap(key ->{
            if (key.getText() == null)  return "Text of element n" + (i[0]++) ;
            else return key.getText();
        }, WebElement::getType));
        System.out.println(elementsResult);

    }

    /**
     * Получить список элементов, у которых текст или значение оканчивается на число от 500 и более.
     * И отсортировать по увеличению сначала элементы с текстом, а затем по убыванию элементы со значением.
     */
    public void test_9() {
        List<WebElement> elements = getElements();
        List<WebElement> elementsFiltered = elements.stream().filter(item ->
                (item.getText() != null && Integer.parseInt(item.getText().substring(16)) >= 500) ||
                (item.getValue() != null && Integer.parseInt(item.getValue().substring(17)) >= 500))
                .toList();
        List<WebElement> elementsFirstPartSorted = elementsFiltered.stream().filter(item -> item.getText() != null)
                .sorted(Comparator.comparing(WebElement::getText)).toList();
        List<WebElement> elementsSecondPartSorted = elementsFiltered.stream().filter(item -> item.getValue() != null)
                .sorted(Comparator.comparing(WebElement::getValue).reversed()).toList();
        elementsFirstPartSorted.add((WebElement) elementsSecondPartSorted);
//        elementsFirstPartSorted.forEach(i -> System.out.println(i.getText() + " " + i.getValue()));

//                        (item_1, item_2) ->{
//                    int result = 0;
//                    if (item_1.getText() != null) {
//                    if (Integer.parseInt(item_1.getText().substring(16)) < Integer.parseInt(item_2.getText().substring(16))) return result = -1;
//                    else if (Integer.parseInt(item_1.getText().substring(16)) > Integer.parseInt(item_2.getText().substring(16))) return result = 1;}
//                    return result;
//                }

    }

    public static Map<Integer, String> getMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");
        map.put(5, "five");
        map.put(6, "six");
        map.put(7, "seven");
        map.put(8, "eight");
        map.put(9, "nine");
        map.put(10, "ten");
        return map;
    }

    public static List<String> getStringList() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.add("five");
        list.add("six");
        list.add("seven");
        list.add("one");
        list.add("nine");
        list.add("ten");
        return list;
    }

    public static List<Integer> getIntList() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        return list;
    }

    public static List<WebElement> getElements() {
        List<WebElement> result = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            result.add(new WebElement());
        }
        return result;
    }
}