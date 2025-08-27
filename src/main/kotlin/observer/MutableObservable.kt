package observer

class MutableObservable<T>(initialValue: T) : Observable<T> {


    private val _observers = mutableListOf<Observer<T>>()
    override val observers
        get() = _observers.toList()

    override var currentValue: T = initialValue
        set(value) {
            field = value
            notifyObservers()
        }

    override fun addObserver(observer: Observer<T>) {
        _observers.add(observer)
        observer.onChanged(currentValue)
    }

    override fun removeObserver(observer: Observer<T>) {
        _observers.remove(observer)
    }
}