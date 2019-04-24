package com.example.teatime;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

public class OrderSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        Toolbar menuToolbar = (Toolbar) findViewById(R.id.order_summary_toolbar);
        setSupportActionBar(menuToolbar);
        getSupportActionBar().setTitle(getString(R.string.order_summary_title));

        Intent intent = getIntent();
        String teaName = intent.getStringExtra(OrderActivity.EXTRA_TEA_NAME);
        int price = intent.getIntExtra(OrderActivity.EXTRA_TOTAL_PRICE, 0);
        String size = intent.getStringExtra(OrderActivity.EXTRA_SIZE);
        String milkType = intent.getStringExtra(OrderActivity.EXTRA_MILK_TYPE);
        String sugarType = intent.getStringExtra(OrderActivity.EXTRA_SUGAR_TYPE);
        int quantity = intent.getIntExtra(OrderActivity.EXTRA_QUANTITY, 0);

        displayOrderSummary(teaName, price, size, milkType, sugarType, quantity);
    }

    /**
     * Create summary of the order.
     *
     * @param teaName   type of tea
     * @param quantity  quantity ordered
     * @param price     price of the order
     * @param milkType  type of milk to add
     * @param sugarType amount of sugar to add
     */
    private void displayOrderSummary(String teaName, int price, String size, String milkType,
                                     String sugarType, int quantity) {

        // Set tea name in order summary
        TextView teaNameTextView = (TextView) findViewById(
                R.id.summary_tea_name);
        teaNameTextView.setText(teaName);

        // Set quantity in order summary
        TextView quantityTextView = (TextView) findViewById(
                R.id.summary_quantity);
        quantityTextView.setText(String.valueOf(quantity));

        // Set tea size in order summary
        TextView sizeTextView = (TextView) findViewById(
                R.id.summary_tea_size);
        sizeTextView.setText(size);

        // Set milk type in order summary
        TextView milkTextView = (TextView) findViewById(
                R.id.summary_milk_type);
        milkTextView.setText(milkType);

        // Set sugar amount in order summary
        TextView sugarTextView = (TextView) findViewById(
                R.id.summary_sugar_amount);
        sugarTextView.setText(sugarType);

        // Set total price in order summary
        TextView priceTextView = (TextView) findViewById(
                R.id.summary_total_price);

        String convertPrice = NumberFormat.getCurrencyInstance().format(price);
        priceTextView.setText(convertPrice);

    }

    /**
     * This method is called when the Send Email button is clicked and sends a copy of the order
     * summary to the inputted email address.
     */

    public void sendEmail(View view) {

        String emailMessage = getString(R.string.email_message);

        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.order_summary_email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, emailMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

        }
    }

}
