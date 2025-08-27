package observer

interface Observable<T> {

    val observers: List<Observer<T>>
    val currentValue: T

    fun addObserver(observer: Observer<T>)
    fun removeObserver(observer: Observer<T>)

    fun notifyObservers() {
        observers.forEach {
            it.onChanged(currentValue)
        }
    }

}