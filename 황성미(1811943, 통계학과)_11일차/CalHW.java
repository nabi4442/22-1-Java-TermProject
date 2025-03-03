import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalHW extends JFrame {

	JLabel label;
	JLabel info;
	double result = 0;
	String math = "";
	double num = 0;
	String tostring = "";

	public CalHW() {
		setTitle("계산기 만들기 : HSM");
		
		setLayout(new BorderLayout(5, 5));
		
		//입력 값과 결과값이 출력되는 위 패널 생성 후 콘테이너에 추가
		UpPanel UP = new UpPanel();
		add(UP, BorderLayout.EAST);

		//버튼을 넣을 아래 패널 생성 후 콘테이너에 추가
		DownPanel DP = new DownPanel();
		add(DP, BorderLayout.SOUTH);
		
		setSize(350, 600);
		setVisible(true);
	}

	//화면 구성
	//사용자가 누른 값을 보여주는 화면
	class UpPanel extends JPanel {
		public UpPanel() {
			setLayout(new GridLayout(3, 1));
			info = new JLabel("");
			label = new JLabel("0");
			
			info.setFont(new Font("맑은 고딕", 0, 40));
			info.setForeground(Color.BLACK);
			info.setHorizontalAlignment(JLabel.RIGHT);
			
			label.setFont(new Font("맑은 고딕", Font.BOLD , 55));
			label.setForeground(Color.BLACK);
			label.setHorizontalAlignment(JLabel.RIGHT);
			
			add(info);
			add(label);
		}
	}
	
	//숫자, 기호 보여주는 화면
	class DownPanel extends JPanel{
		public DownPanel() {
			JButton[] bt = new JButton[20];
			setLayout(new GridLayout(5, 4, 5, 5));
			setBackground(Color.DARK_GRAY);
			
			bt[0] = new JButton("×");
			bt[1] = new JButton("÷");
			bt[2] = new JButton("AC");
			bt[3] = new JButton("C");

			bt[4] = new JButton("7");
			bt[5] = new JButton("8");
			bt[6] = new JButton("9");
			bt[7] = new JButton("√");
			
			bt[8] = new JButton("4");
			bt[9] = new JButton("5");
			bt[10] = new JButton("6");
			bt[11] = new JButton("-");
			
			bt[12] = new JButton("1");
			bt[13] = new JButton("2");
			bt[14] = new JButton("3");
			bt[15] = new JButton("+");
			
			bt[16] = new JButton("＾2");
			bt[17] = new JButton("0");
			bt[18] = new JButton(".");
			bt[19] = new JButton("=");
			
			
			for(int i = 0; i <= 19; i++) {

				//화면 구성
				bt[i].setPreferredSize(new Dimension(0, 65));
				bt[i].setFont(new Font("맑은 고딕", 0, 20));
				bt[i].setForeground(Color.BLACK);
				bt[i].setBackground(new Color(240, 240, 240));

				add(bt[i]);

				//이벤트 핸들링(non-named class & named class이용) 
				if(3 < i && i < 15 && i % 4 != 3) { //숫자 버튼
					bt[i].addActionListener(new ActionListener() {
						//non-name class event
						@Override
						public void actionPerformed(ActionEvent e) {
							
							JButton b = (JButton)e.getSource();
							if(label.getText() == "0") {
								label.setText("");
							}
							String labeltext = label.getText();
							String text = b.getText();
							String newtext = labeltext + text;
							int n = newtext.length();
							if(n <= 10) {
								label.setText(newtext);
							}
							if(info.getText().contains("=") == true) {
								info.setText("");
								label.setText(b.getText());
								result = 0;
								num = 0;
							}
						}
					});
				}else if(i == 17) { // 0 버튼
					bt[i].addActionListener(new ActionListener() {
						//non-name class event
						@Override
						public void actionPerformed(ActionEvent e) {
							JButton b = (JButton)e.getSource();
							String labeltext = label.getText();
							String text = b.getText();
							String newtext = labeltext + text;
							if(labeltext == "0") {
								label.setText("0");
							}else {
								label.setText(newtext);
							}
							
						}
					});
				}else if(i == 19) { // = 버튼
					bt[i].setBackground(new Color(153, 204, 255));
					bt[i].addActionListener(new Result()); //Result class event
				}else if(i % 4 == 3 || i < 3 || 15 < i) {
					bt[i].setBackground(new Color(102, 153, 255));
					if(i == 2) { // AC버튼
							bt[i].addActionListener(new ActionListener() {
							//non-name class event
							@Override
							public void actionPerformed(ActionEvent e) {
								int n = label.getText().length();
								if(n >= 0) {
									label.setText("0");
									info.setText("");
								}
								result = 0;
							}
						});
					}else if(i == 3) { //C 버튼
						bt[i].addActionListener(new ActionListener() {
							//non-name class event
							@Override
							public void actionPerformed(ActionEvent e) {
								int n = label.getText().length();
								if(n > 0) {
									setBackSpace(getBackSpace().substring(0, getBackSpace().length() - 1));
								}
								if(label.getText() == "") {
									label.setText("0");
								}
							}
						});
					}else if(i == 18) { // .버튼
						bt[i].addActionListener(new ActionListener() {
							//non-name class event
							@Override
							public void actionPerformed(ActionEvent e) {
								JButton b = (JButton)e.getSource();
								String labeltext = label.getText();
								String text = b.getText();
								String newtext = labeltext + text;
								int n = newtext.length();
								if(label.getText().contains(b.getText()) == false && n < 10) {
										label.setText(newtext);
								}
							}
						});
					}else { //연산자 기호
						bt[i].addActionListener(new Result());//Result class event
					}
				}
				
			}
		}		
	}
	
	//Result class event
	public class Result implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			String labeltext = label.getText();
			String text = b.getText();
			String newtext = labeltext + text;
			int n = newtext.length();
			if(text != "＾2") {
				num = Double.parseDouble(label.getText().substring(0, n - 1));				
			}
			if(math == "+") {
				result += num;
				math = "";
			}else if(math == "-") {
				result -= num;
				math = "";
			}else if(math == "×") {
				result *= num;
				math = "";
			}else if(math == "÷") {
				result /= num;
				math = "";
			}
			
			if(math == "") {
				math = b.getText();			
			}
			if(info.getText() == "" && text != "＾2" && text != "=") {
				info.setText(newtext);
				result = num;
				label.setText("0");
			}else if(info.getText() != "" && text != "＾2" && text != "="){
				result = (Math.round(result*1000000000)/1000000000.0);
				if(result % 1 == 0) {
					info.setText((int) result + text);
					label.setText("0");
				}else {
					info.setText(result + text);
					label.setText("0");
				}
			}
			if(text == "＾2") {
				num = Double.parseDouble(label.getText().substring(0, n - 2));
				math = "";
				if(info.getText() == "") {
					result = (Math.round((Math.pow(num, 2))*1000000000)/1000000000.0);
					if(result % 1 == 0) {
						info.setText("pow(" + (int) num + ")");
						label.setText(String.valueOf((int) result));
					}else {
						info.setText("pow(" + num + ")");
						label.setText(String.valueOf(result));
					}
				}else {
					if(result % 1 == 0) {
						info.setText("pow(" + (int) result + ")");
						result = (Math.round((Math.pow(result, 2))*1000000000)/1000000000.0);
						label.setText(String.valueOf((int) result));
					}else {
						info.setText("pow(" + result + ")");
						result = (Math.round((Math.pow(result, 2))*1000000000)/1000000000.0);
						label.setText(String.valueOf(result));
					}
				}
			}
			if(text == "√") {
				math = "";
				if(info.getText() == "") {
					result = (Math.round((Math.sqrt(num))*1000000000)/1000000000.0);
					if(result % 1 == 0) {
						info.setText("sqrt(" + (int) num + ")");
						label.setText(String.valueOf((int) result));
					}else {
						info.setText("sqrt(" + num + ")");
						label.setText(String.valueOf(result));
					}
				}else {
					if(Math.sqrt(result) % 1 == 0) {
						info.setText("sqrt(" + (int) result + ")");
						result = (Math.round((Math.sqrt(result))*1000000000)/1000000000.0);
						label.setText(String.valueOf((int) result));
					}else {
						info.setText("sqrt(" + result + ")");
						result = (Math.round((Math.sqrt(result))*1000000000)/1000000000.0);
						label.setText(String.valueOf(result));
					}
				}
			}
			if(text == "=") {
				math = "";
				if(info.getText() != "" && label.getText() != "" && info.getText().contains(text) == false) {
					if(result % 1 == 0) {
						info.setText(info.getText() + label.getText() + text);
						label.setText(String.valueOf((int) result));
					}else {
						info.setText(info.getText() + label.getText() + text);
						label.setText(String.valueOf(result));
					}
				}
			}
		}
	}
	
	private void setBackSpace(String bs) {
		label.setText(bs);
	}
	
	private String getBackSpace() {
		return label.getText();
	}
}
