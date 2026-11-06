public class CardPayment implements PaymentService {
    @Override
    public void pay(double amount) {
        System.out.println("Paid by card: $" + amount);
    }
}