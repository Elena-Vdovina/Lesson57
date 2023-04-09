public class Homework {

// Задана процентная ставка по кредиту (percent % годовых), срок кредитования (months месяцев)
// и сумма кредита (total евро).
// Необходимо при помощи бинарного поиска рассчитать размер аннуитетный платежа:
// платежи каждый месяц должны быть равными, как при обычном кредите или ипотеке.
// Каждый платёж полностью выплачивает все накопленные проценты, оставшаяся сумма идёт
// на погашение тела кредита.
// Это означает, что в первом месяце ваш платёж состоит почти только из начисленных процентов,
// а в последнем - почти только из тела кредита.

// Подсказка 1
// Воспользуйтесь бинарным поиском для размера начисляемых ежемесячно процентов
// (с точностью до четырёх знаков после запятой) - это не percent / 12.
// Процент должен быть таким, чтобы за 12 месяцев на сумму (с учётом начисления процентов
// на проценты) общая сумма долга увеличилась на percent %.
//
// Подсказка 2
// Подберите размер ежемесячного платежа при помощи бинарного поиска: необходимо,
// чтобы при выбранном размере платежа и ежемесячных процентах из подсказки 1 количество
// платежей для полного погашения долга (total евро) совпадало с заданным в условии задачи
// (months месяцев).

  public static double ACCURACY = 0.0001;

  public static void main(String[] args) {
    double percent = 12.0;
    int month = 24;
    int total = 400000;
    // По калькулятору
    // Ежемесячный платеж 18829,39
    // Начисленные проценты 51905,36
    // Долг + проценты 451905,36

    double rateMonth = rate(total, percent);
    double paymentMonth = payment(total, percent, month);

    System.out.printf("Процентная ставка в месяц %.6f%n", rateMonth);
  }

  /**
   * Поиск размера начисляемых ежемесячно процентов
   *
   * @param total   сумма кредитования
   * @param percent ставка кредита
   * @return размер начисляемых ежемесячно процентов
   */
  public static double rate(int total, double percent) {
    double left = 0; // 0 точно меньше нашего ответа
    double right = total; // x точно больше нашего ответа
    double total1 = total * (1 + percent / 100);
    // double total2 = -1;
    while (right - left > ACCURACY) {
      double mid = (left + right) / 2;
      double total2 = total * mid * 12;
      if (total2 == total1 - total) {
        // если mid и есть наш квадратный корень - так почти никогда не бывает
        return mid;
      }
      // если mid меньше настоящего квадратного корня
      if (total2 < total1 - total) { // если mid меньше настоящего квадратного корня
        left = mid; // мы не можем ничего добавить - может быть, нам не хватило совсем немного
      } else { // не равно и не меньше, значит, больше
        right = mid;
      }
    }
    return left;
  }

  /**
   * Поиск размера ежемесячного платежа
   *
   * @param total   сумма кредита
   * @param percent ставка по кредиту
   * @param month   срок кредитования
   * @return размер ежемесячного платежа
   */
  public static double payment(int total, double percent, int month) {
    double left = 0; // 0 точно меньше нашего ответа
    double right = total; // x точно больше нашего ответа
    double p = percent / 12 / 100;
    double total1 = total * p / (1 - Math.pow(1 + p, -month)) * month;
    while (right - left > ACCURACY) {
      double mid = (left + right) / 2;
      double total2 = mid * month;
      if (total1 == total2) {
        return mid;
      }
      if (total2 < total1) {
        left = mid;
      } else {
        right = mid;
      }
    }
    return left;
  }

}
