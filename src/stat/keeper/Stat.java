package stat.keeper;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Stat implements OnClickListener{
	private int value = 0;
	
	private Button plus, minus;
	private TextView count;
	
	Stat(Button plusButton, Button minusButton, TextView counter){
		plus = plusButton;
		minus = minusButton;
		count = counter;
		value = Integer.parseInt(count.getText().toString());
		
		plus.setOnClickListener(this);
		minus.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		value = Integer.parseInt(count.getText().toString());
		if(v.getId() == plus.getId()){
			value++;
			count.setText(Integer.toString(value));
		}else if(v.getId() == minus.getId() && (value-1) >= 0){
			value--;
			count.setText(Integer.toString(value));
		}
	}
	
	public void toggleButtons(){
		if (plus.getVisibility() == View.VISIBLE) {
			plus.setVisibility(View.INVISIBLE);
			minus.setVisibility(View.INVISIBLE);
		}else{
			plus.setVisibility(View.VISIBLE);
			minus.setVisibility(View.VISIBLE);
		}
	}
	
	public int getCount(){
		return Integer.parseInt(count.getText().toString());
	}
}
