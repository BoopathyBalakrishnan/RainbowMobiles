package com.rainbowmobiles.app.complaints;

import java.util.Calendar;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.rainbowmobiles.app.complaints.bo.RainbowComplaintBO;
import com.rainbowmobiles.app.complaints.constants.ApplicationConstants;
import com.rainbowmobiles.app.complaints.util.MyGmailSession;
import com.rainbowmobiles.app.complaints.util.UserEmailFetcher;
import com.rainbowmobiles.app.complaints.validator.CompliantFormValidator;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initWidgets();
		Button login = (Button) findViewById(R.id.sendbtn);
		login.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				RainbowComplaintBO rainbowComplaintBO = new RainbowComplaintBO();
				rainbowComplaintBO.setUserName(userName.getText().toString());
				rainbowComplaintBO.setUserPhoneNo(userPhoneNo.getText().toString());
				rainbowComplaintBO.setUserInvoiceNo(invoiceNo.getText().toString());
				rainbowComplaintBO.setUserCompliantDt(date.getText().toString());
				rainbowComplaintBO.setIssueSummary(msg.getText().toString());
				
				if (validateCompliantForm()) {
					try {
						regidterEmailComplaint(rainbowComplaintBO);
						finish();
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(),
								"Please Check Internet Connection try again!", Toast.LENGTH_SHORT).show();
					}
					
				} else {
					Toast.makeText(getApplicationContext(), "Please correct the Errors...", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	
	/**
	 * 
	 */
	private void initWidgets() {
		splashLayout = (ImageView) findViewById(R.id.splash_frm_splashscreenimg);
		userName = (EditText) findViewById(R.id.userName);
		userName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            	CompliantFormValidator.hasText(userName);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
		msg = (EditText) findViewById(R.id.message);
		msg.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            	CompliantFormValidator.hasText(msg);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
		userPhoneNo = (EditText) findViewById(R.id.userPhNo);
		userPhoneNo.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            	CompliantFormValidator.hasText(userPhoneNo);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
		date = (EditText) findViewById(R.id.userDate);
		date.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            	CompliantFormValidator.hasText(date);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
		userDate = (ImageButton) findViewById(R.id.datePicker);
		invoiceNo = (EditText) findViewById(R.id.userInvoiceNo);
		invoiceNo.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            	CompliantFormValidator.hasText(invoiceNo);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
		
		userDate.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				selectedDate = Calendar.getInstance();
				day = selectedDate.get(Calendar.DAY_OF_MONTH);
				month = selectedDate.get(Calendar.MONTH);
				year = selectedDate.get(Calendar.YEAR);
				showDialog(0);
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
			date.setText(selectedDay + "/" + (selectedMonth + 1) + "/"+ selectedYear);
		}
	};
	

	/**
     * Validate the Complaint Form
     * 
     * @return
     */
    public boolean validateCompliantForm() {
        boolean ret = true;
 
        if (!CompliantFormValidator.hasText(userName)) {
        	ret = false;
        }
        if (!CompliantFormValidator.isValid(userPhoneNo, CompliantFormValidator.PHONE_REGEX, CompliantFormValidator.PHONE_MSG, true)) {
        	ret = false;
        }
        if (!CompliantFormValidator.hasText(invoiceNo)) {
        	ret = false;
        }
        if (!CompliantFormValidator.hasText(date)) {
        	ret = false;
        }
        if (!CompliantFormValidator.hasText(msg)) {
        	ret = false;
        }
        return ret;
    }
    
	/**
	 * @throws MessagingException 
	 * @throws AddressException 
	 * 
	 */
	private void regidterEmailComplaint(final RainbowComplaintBO rainbowComplaintBO) throws AddressException, MessagingException{
		Message message = new MimeMessage(MyGmailSession.getMyGmailSession());
		//message.setFrom(new InternetAddress("urnanjun@gmail.com"));
		String userEmail = UserEmailFetcher.getEmail(getApplicationContext());
		message.setFrom(new InternetAddress(userEmail));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(ApplicationConstants.GMAIL_COMPLAINT_EMAIL_TO));

		StringBuilder emailSubject = new StringBuilder(
				"New Complaint - ");
		emailSubject.append("Name::" + rainbowComplaintBO.getUserName() + ", ");
		emailSubject.append("Phone No::" + rainbowComplaintBO.getUserPhoneNo() + ", ");
		emailSubject.append("Date::" + rainbowComplaintBO.getUserCompliantDt() + ", ");
		emailSubject.append("Invoice No::" + rainbowComplaintBO.getUserInvoiceNo());
		message.setSubject(emailSubject.toString());
		StringBuilder emailMessage = new StringBuilder(
				"<html><body style=\"font-family:sans-serif Arial\">");
		emailMessage
				.append("<div style=\"font-weight:normal; font-size:100%\">");
		emailMessage.append("Name::" + rainbowComplaintBO.getUserName() + "<br/> ");
		emailMessage.append("Phone No::" + rainbowComplaintBO.getUserPhoneNo() + "<br/> ");
		emailMessage.append("Date::" + rainbowComplaintBO.getUserCompliantDt() + "<br/> ");
		emailMessage.append("Invoice No::" + rainbowComplaintBO.getUserInvoiceNo()
				+ "<br/> ");
		emailMessage.append("Issue Summary::" + rainbowComplaintBO.getIssueSummary()
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
	}
}
