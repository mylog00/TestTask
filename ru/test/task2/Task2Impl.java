package ru.test.task2;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * <h1>Задание №2</h1>
 * Реализуйте интерфейс {@link IElementNumberAssigner}.
 *
 * <p>Помимо качества кода, мы будем обращать внимание на оптимальность предложенного алгоритма по времени работы
 * с учетом скорости выполнения операции присвоения номера:
 * большим плюсом (хотя это и не обязательно) будет оценка числа операций, доказательство оптимальности
 * или указание области, в которой алгоритм будет оптимальным.</p>
 */
public class Task2Impl implements IElementNumberAssigner {

    // ваша реализация должна работать, как singleton. даже при использовании из нескольких потоков.
    private static final IElementNumberAssigner INSTANCE = new Task2Impl();

	// глобальная копия списка для присвоения
	private List<IElement> elements = null;
	// буферное число bufInt ∉ {number[0], number[1], ... number[n]} (number[i] = elements.get(i).getNumber())
	private int bufInt = -1;

	/**
	 * Худшее время выполнения будет если список состоит из пар элементов которые нужно поменять между собой
	 * тогда количество перестановок будет (N + N/2)
	 * В среднем количество перестановок будет N для не упорядоченных списков
	 * и будет снижаться если часть номеров стоят на своих местах
	 * Учитывая что наиболее длительная операция  присвоение номера {@link IElement#setupNumber(int)}
	 * то время выполнения будет порядка O(N)
	 */
    @Override
    public void assignNumbers(final List<IElement> elements) {

		// устанавливаем глобальную копию списка для присвоения
		this.elements = elements;

		// если все элементы были установлены в нужном порядке и работа завершена
		if (preSort()) return;

		// иначе, проходимся по списку и устанавливаем оставшиеся элементы
		//for(IElement elem: elements){
		for(int index = 0; index < elements.size(); index++) {
			//int index = elements.indexOf(elem);
			if( index != elements.get(index).getNumber()) {this.bufInt = trySetNumber(index);}
		}

    }

	/**
	 * Метод пытается установить все номера из диапазоне от 0 до n не попавшие в список
	 * @return {@code true} если получилось установить все элементы
	 */
	private boolean preSort() {
		// счетчик количества номеров не принадлежащих диапазону от 0 до n
		int count = 0;

		// создаем временный список номеров всех элементов
		List<Integer> listOfAllNumbers = new ArrayList<>();
		// копируем все номера во временный список для упрощения поиска по ним
		for(IElement elem: this.elements){
			listOfAllNumbers.add(elem.getNumber());
		}
		// Защищаем список от изменений ()
		listOfAllNumbers = unmodifiableList(listOfAllNumbers);

		// проверяем все ли номера из диапазона от 0 до n попали в список
		for (int i = 0; i < listOfAllNumbers.size(); i++){
			int index = listOfAllNumbers.indexOf(i);
			// если номер не попал
			if(index < 0){
				// пытаемся установить его на свое место и запоминаем замещеное число в буфер
				this.bufInt = i;
				this.bufInt = trySetNumber(i);
				count++;
			}
		}

		// если все элементы списка были за пределами диапазона от 0 до n
		// значит они все были установлены в нужном порядке и работа завершена
		return ( count >= this.elements.size() );
	}

	/**
	 * Пытается заменить число у элемента по индексу {@code number} числом из {@code bufInt}
	 * @param number индекс элемента {@link IElement} в котором нужно поменять число
	 * @return замененное значение
	 */
	private int trySetNumber(int number){
		// получаем номер соответствующего элемента списка
		IElement elem = this.elements.get(number);
		int elemNumber = elem.getNumber();

		// устанавливаем номером элемента значение буферного числа
		elem.setupNumber(this.bufInt);


		// если заменяемое число не принадлежит диапазону от 0 до n то возвращаем его
		if( (elemNumber < 0) || (elemNumber >= this.elements.size()) ){
			return elemNumber;
		}

		// запоминаем новое значение
		this.bufInt = elemNumber;
		// пытаемся установить элемент по индексу elemNumber
		elemNumber = trySetNumber( elemNumber );
		return elemNumber;
	}



	/**
	 * Возвращает instance объекта реализующего интерфейс IElementNumberAssigner
	 * @return объект реализующий интерфейс IElementNumberAssigner
	 */
	public static IElementNumberAssigner getInstance(){
		return INSTANCE;
	}

}
