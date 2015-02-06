package mobile.traffic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class MainActivity extends Activity
{
	private Handler mHandler = new Handler();
	private long mGsmRx = 0;
	private long mGsmTx = 0;
	private long mTotalRx = 0;
	private long mTotalTx = 0;
	
	TextView tvOperator = null;
	TextView tvGsmRx = null;
	TextView tvGsmTx = null;
//	TextView tvTotalRx = null;
//	TextView tvTotalTx = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvOperator = (TextView)this.findViewById(R.id.textViewTelecom);
		tvGsmRx = (TextView) this.findViewById(R.id.textViewRx);
		tvGsmTx = (TextView) this.findViewById(R.id.textViewTx);
//		tvTotalRx = (TextView)this.findViewById(R.id.textViewTotalRx);
//		tvTotalTx = (TextView)this.findViewById(R.id.textViewTotalTxByte);
		
		/** Get Operator name**/
		TelephonyManager teleManager = ((TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE));
		String strOperator = teleManager.getSimOperatorName();//teleManager.getNetworkOperatorName();
		tvOperator.setText(strOperator);
		
		/** Get GSM Received traffic data **/
		mGsmRx = TrafficStats.getMobileRxBytes();
		mGsmTx = TrafficStats.getMobileTxBytes();
		
		if(mGsmRx == TrafficStats.UNSUPPORTED || mGsmTx == TrafficStats.UNSUPPORTED)
		{
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("GSM Traffic");
			alert.setMessage("Your device does not support traffic state monitoring.");
			alert.show();
		}
		else
		{
			mHandler.postDelayed(mRunnable, 1000);
		}
		
	}
	
	private final Runnable mRunnable = new Runnable()
	{

		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			mGsmRx = TrafficStats.getMobileRxBytes();
			mGsmTx = TrafficStats.getMobileTxBytes();
			mTotalRx = TrafficStats.getTotalRxBytes();
			mTotalTx = TrafficStats.getTotalTxBytes();
			
			MainActivity.this.tvGsmRx.setText(Long.toString(mGsmRx));
			MainActivity.this.tvGsmTx.setText(Long.toString(mGsmTx));
			
			mHandler.postDelayed(mRunnable, 1000);
		}
		
	};
}
