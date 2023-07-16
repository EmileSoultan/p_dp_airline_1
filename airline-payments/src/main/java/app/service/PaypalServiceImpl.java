package app.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaypalServiceImpl implements PaypalService {

    private final APIContext apiContext;

    @Override
    public Payment createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String description ,
            String cancelUrl,
            String successUrl
            ) throws PayPalRESTException {
        var amount = new Amount(); // заменила в этой строке
        amount.setCurrency(currency);
        total = BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP).doubleValue();

        amount.setTotal(total.toString());

        var transaction = new Transaction();// заменила в этой строке
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        var payer = new Payer();// заменила в этой строке
        payer.setPaymentMethod(method);

        var payment = new Payment();// заменила в этой строке
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        var redirectUrls = new RedirectUrls();// заменила в этой строке
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        var payment = new Payment();// замена
        payment.setId(paymentId);
        var paymentExecute = new PaymentExecution();// замена
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }
}
