package RMI_EXAMPLES;
import java.rmi.*;

public interface BillingService extends Remote
{
    // Регистрация новой карты
    public void addNewCard(Card card) throws RemoteException;

    // Добавление денежных средств на карту
    public void addMoney(Card card, double money)
            throws RemoteException;
    // Снятие денежных средств с карты
    public void subMoney(Card card, double money)
            throws RemoteException;
    // Получение баланса карты
    public double getCardBalance(Card card)
            throws RemoteException;
}