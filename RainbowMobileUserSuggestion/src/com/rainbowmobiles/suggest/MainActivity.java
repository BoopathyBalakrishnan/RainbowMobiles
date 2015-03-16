package com.rainbowmobiles.suggest;

import java.util.Calendar;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class MainActivity extends Activity {

	EditText userName;
	EditText msg;
	EditText userPhoneNo;
	ImageView splashLayout;
	ImageButton userDate;
	EditText date;
	EditText invoiceNo;
	String possibleEmail;
	Calendar selectedDate;
	int day;
	int month;
	int year;
	String inputDate;
	ScrollView userInputPage;
	RelativeLayout errorPage;
	ImageButton closeBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		initWidgets();
		userInputPage.setVisibility(View.VISIBLE);
		errorPage.setVisibility(View.GONE);
		Button login = (Button) findViewById(R.id.sendbtn);
		login.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				String inputUserName = userName.getText().toString();
				String inputPhoneNo = userPhoneNo.getText().toString();
				inputDate = date.getText().toString();
				String inputInvoiceNo = invoiceNo.getText().toString();
				String inputIssueDetails = msg.getText().toString();
				try {
					Message message = new MimeMessage(MyGmailSession
							.getMyGmailSession());
					// String userEmail =
					// UserEmailFetcher.getEmail(getApplicationContext());
					message.setFrom(new InternetAddress("urnanjun@gmail.com"));
					// message.setFrom(new InternetAddress(userEmail));
					message.setRecipients(
							Message.RecipientType.TO,
							InternetAddress
									.parse(ApplicationConstants.GMAIL_COMPLIANT_EMAIL_TO));

					StringBuilder emailSubject = new StringBuilder(
							"Compliant - ");
					emailSubject.append("Name::" + inputUserName + ", ");
					emailSubject.append("Phone No::" + inputPhoneNo + ", ");
					emailSubject.append("Date::" + inputDate + ", ");
					emailSubject.append("Invoice No::" + inputInvoiceNo);
					message.setSubject(emailSubject.toString());
					StringBuilder emailMessage = new StringBuilder(
							"<html><body style=\"font-family:sans-serif Arial\">");
					emailMessage
							.append("<div style=\"font-weight:normal; font-size:100%\">");
					emailMessage.append("Name::" + inputUserName + "<br/> ");
					emailMessage.append("Phone No::" + inputPhoneNo + "<br/> ");
					emailMessage.append("Date::" + inputDate + "<br/> ");
					emailMessage.append("Invoice No::" + inputInvoiceNo
							+ "<br/> ");
					emailMessage.append("Issue Summary::" + inputIssueDetails
							+ "<br/><br/><br/> ");
					emailMessage.append("</div>");
					emailMessage
							.append("<div style=\"font-weight:normal; font-size:75%\">");
					emailMessage.append("Thanks," + "<br/> ");
					emailMessage.append("Issue Reported from AndroidApp");
					emailMessage.append("</div>");
					emailMessage.append("</body></html>");
					message.setContent(emailMessage.toString(),
							"text/html; charset=utf-8");

					Transport.send(message);

					Intent invokeSuccessActivity = new Intent(
							getApplicationContext(), SuccessActivity.class);
					startActivity(invokeSuccessActivity);

				} catch (MessagingException e) {

					/*
					 * e.printStackTrace(); throw new RuntimeException(e);
					 */
					userInputPage.setVisibility(View.GONE);
					errorPage.setVisibility(View.VISIBLE);

				}

			}
		});
	}

	/**
	 * 
	 */
	private void initWidgets() {
		userInputPage = (ScrollView) findViewById(R.id.userInputs);
		errorPage = (RelativeLayout) findViewById(R.id.error_screen);
		splashLayout = (ImageView) findViewById(R.id.splash_frm_splashscreenimg);
		userName = (EditText) findViewById(R.id.userName);
		msg = (EditText) findViewById(R.id.message);
		userPhoneNo = (EditText) findViewById(R.id.userPhNo);
		date = (EditText) findViewById(R.id.userDate);
		closeBtn = (ImageButton) findViewById(R.id.errorCloseBtn);
		userDate = (ImageButton) findViewById(R.id.datePicker);
		invoiceNo = (EditText) findViewById(R.id.userInvoiceNo);
		userDate.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				selectedDate = Calendar.getInstance();
				day = selectedDate.get(Calendar.DAY_OF_MONTH);
				month = selectedDate.get(Calendar.MONTH);
				year = selectedDate.get(Calendar.YEAR);
				showDialog(0);
			}
		});
		closeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	/**
	 * 
	 */
	protected Dialog onCreateDialog(int id) {
		return new DatePickerDialog(this, datePickerListener, year, month, day);
	}

	/**
	 * 
	 */
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			date.setText(selectedDay + "/" + (selectedMonth + 1) + "/"
					+ selectedYear);
		}
	};
}