package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the "+" button is clicked.
     */
    public void increment(View view) {
        quantity = quantity + 1;
        if (quantity > 100) {
            quantity = 100;
            showToast(getString(R.string.maxOrder));
            return;
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the "-" button is clicked.
     */
    public void decrement(View view) {
        quantity = quantity - 1;
        if (quantity < 1) {
            quantity = 1;
            showToast(getString(R.string.minOrder));
            return;
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {

        displayMessage(createOrderSummary());
    }

    /**
     * Calculates the price of the order.
     * <p>
     * //  * @param quantity is the number of cups of coffee ordered
     */
    @org.jetbrains.annotations.Contract(pure = true)
    private int calculatePrice() {
        int price = quantity * pricePerCup();
        return price;
    }

//    This method is calculates the price of one cup.

    private int pricePerCup() {
        CheckBox checkWipedCream = findViewById(R.id.chek_wiped_cream);
        CheckBox checkChocolate = findViewById(R.id.chek_chocolate);
        int price = 25;

        if (checkWipedCream.isChecked())
            price = price + 2;

        if (checkChocolate.isChecked())
            price = price + 3;

        return price;
    }

    /**
     * This method is called when the checkbox is clicked.
     */

    public void refreshPrice(View view) {
        displayPrice(pricePerCup());
    }

    /**
     * This method is checks wiped cream checkbox.
     */

    private String isWipedCream() {
        CheckBox checkWipedCream = findViewById(R.id.chek_wiped_cream);
        String msg;
        if (checkWipedCream.isChecked())
            msg = getString(R.string.withWipedCream);
        else
            msg = getString(R.string.withOutWipedCream);
        return msg;
    }

    /**
     * This method is checks chocolate checkbox.
     */

    private String isChocolate() {
        CheckBox checkChocolate = findViewById(R.id.chek_chocolate);
        String msg;
        if (checkChocolate.isChecked())
            msg = getString(R.string.withChocolate);
        else
            msg = getString(R.string.withOutChocolate);
        return msg;
    }

    /**
     * This method is reads user name from editTextView.
     */

    private String userName() {
        EditText userNameText = (EditText) findViewById(R.id.user_name_text);
        String userName = userNameText.getText().toString();
        return userName;
    }

    /**
     * This method is called when the sendEmail button is clicked.
     */

    public void sendEmail(View view) {
        Intent intent = new Intent((Intent.ACTION_SEND));
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, "v.trynoha@gmail.com");
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary());
        startActivity(Intent.createChooser(intent, "Send Email"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is creates summary text of order.
     */

    private String createOrderSummary() {
        int price = calculatePrice();
        String wipedCream = isWipedCream();
        String chocolate = isChocolate();
        String userName = userName();
        String message = getString(R.string.name) + ": " + userName;
        message += "\n" + getString(R.string.quanyityCups) + ": " + quantity;
        message += "\n" + wipedCream;
        message += "\n" + chocolate;
        message += "\n" + getString(R.string.total) + ": " + price + " ₴";
        message += "\n" + getString(R.string.thankYou);
        return message;
    }

 /*   *
     * This method displays the given text on the screen.*/

    private void displayMessage(String message) {
     TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
     orderSummaryTextView.setText(message);
   }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method is displays price of one cup of coffee.
     */

    private void displayPrice(int price) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText("" + price + "₴");
    }

    /**
     * This method is displays error message.
     */

    public void showToast(String text) {
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 400);
        toast.show();
    }

}