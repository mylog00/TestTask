package ru.test.task1;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <h1>Задание №1</h1>
 * Реализуйте интерфейс {@link IStringRowsListSorter}.
 *
 * <p>Мы будем обращать внимание в первую очередь на структуру кода и владение стандартными средствами java.</p>
 */
public class Task1Impl implements IStringRowsListSorter {

    // ваша реализация должна работать, как singleton. даже при использовании из нескольких потоков.
    private static final IStringRowsListSorter INSTANCE = new Task1Impl();

	public static final int EQU_VALUE = 0;
	public static final int NEGATIVE_VALUE = -1;
	public static final int POSITIVE_VALUE = 1;

	@Override
    public void sort(final List<String[]> rows, final int columnIndex) {

		Collections.sort(rows, new Comparator<String[]>() {
			@Override
			public int compare(final String[] o1, final String[] o2) {
				//Производим сравнение элементов по индексу
				final String s1 = o1[columnIndex];
				final String s2 = o2[columnIndex];
				// если один из элементов null, то сравниваем строку с null
				if(s1 == null || s2 == null){
					return compareToNull(s1, s2);
				}
				else {
					return s1.compareTo(s2);
				}
			}
		});
		// напишите здесь свою реализацию. Мы ждем от вас хорошо структурированного, документированного и понятного кода.
    }

	/**
	 * Сравнивает строки если одна из них null
	 * @param s1 строка для сравнения
	 * @param s2 сравниваемая строка
	 * @return 0 если строки равны, 1 - если сравниваемая строка не null, -1 если сравниваемая строка не null
	 */
	private int compareToNull(final String s1, final String s2) {
		if(s1 == null &&  s2 == null) {
			return EQU_VALUE;
		}
		if(s1 == null){
			return NEGATIVE_VALUE;
		}
		else {
			return POSITIVE_VALUE;
		}
	}

	/**
	 * Возвращает instance объекта реализующего интерфейс IStringRowsListSorter
	 * @return объект реализующий интерфейс IStringRowsListSorter
	 */
	public static IStringRowsListSorter getInstance(){
		return INSTANCE;
	}
}
