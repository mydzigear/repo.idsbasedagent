package JSniffer.ui;
import jpcap.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JDCaptureDialog extends JDialog implements ActionListener
{
	static Jpcap jpcap=null;

	String[] devices,dscrs;

	JComboBox adapterComboBox;
	JTextField filterField,caplenField;
	JRadioButton wholeCheck,headCheck,userCheck;
	JCheckBox promiscCheck;

 //       JDFrame frame;

	public JDCaptureDialog(JFrame parent){
		super(parent,"�����������ô���",true);

  //              this.frame=parent;
		devices=Jpcap.getDeviceList();
		dscrs=Jpcap.getDeviceDescription();
		if(devices==null){
			JOptionPane.showMessageDialog(parent,"δ�����豸.");
			dispose();
			return;
		}else if(dscrs==null){
			adapterComboBox=new JComboBox(devices);
		}else{
			adapterComboBox=new JComboBox(dscrs);
		}

//////////////////////////////////////////////////////////////////////////////////////////
		JPanel adapterPane=new JPanel();

		adapterPane.setBorder(BorderFactory.createTitledBorder("ѡ������豸"));
		adapterPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		promiscCheck=new JCheckBox("���豸���óɻ��ģʽ");
		promiscCheck.setSelected(true);
//		promiscCheck.setAlignmentX(Component.LEFT_ALIGNMENT);
                adapterPane.setLayout(new BorderLayout());
                adapterPane.add(adapterComboBox,BorderLayout.NORTH);
                adapterPane.add(promiscCheck,BorderLayout.CENTER);

//////////////////////////////////////////////////////////////////////////////////////
		filterField=new JTextField(20);
		//filterField.setMaximumSize(new Dimension(Short.MAX_VALUE,20));

		JPanel filterPane=new JPanel();
		filterPane.add(new JLabel("����"));
		filterPane.add(filterField);
		filterPane.setBorder(BorderFactory.createTitledBorder("���˲�������"));
		filterPane.setAlignmentX(Component.LEFT_ALIGNMENT);
/////////////////////////////////////////////////////////////////////////////////////////

		JPanel caplenPane=new JPanel();
		caplenPane.setLayout(new BoxLayout(caplenPane,BoxLayout.X_AXIS));
		caplenField=new JTextField("2048");
		caplenField.setEnabled(false);
		caplenField.setMaximumSize(new Dimension(Short.MAX_VALUE,20));
		wholeCheck=new JRadioButton("�������ݰ�");
		wholeCheck.setSelected(true);
		wholeCheck.setActionCommand("Whole");
		wholeCheck.addActionListener(this);
		headCheck=new JRadioButton("ֻ����ͷ��");
		headCheck.setActionCommand("Head");
		headCheck.addActionListener(this);
		userCheck=new JRadioButton("����");
		userCheck.setActionCommand("Other");
		userCheck.addActionListener(this);
		ButtonGroup group=new ButtonGroup();
		group.add(wholeCheck);
		group.add(headCheck);
		group.add(userCheck);
		caplenPane.add(caplenField);
		caplenPane.add(wholeCheck);
		caplenPane.add(headCheck);
		caplenPane.add(userCheck);
		caplenPane.setBorder(BorderFactory.createTitledBorder("�������ݰ���������"));
		caplenPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
///////////////////////////////////////////////////////////////////////////////////////////////

		JPanel buttonPane=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton okButton=new JButton("ȷ��");
		okButton.setActionCommand("OK");
		okButton.addActionListener(this);
		JButton cancelButton=new JButton("ȡ��");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(this);
		buttonPane.add(okButton);
		buttonPane.add(cancelButton);
		buttonPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
////////////////////////////////////////////////////////////////////////////////////////////////////


		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(adapterPane,BorderLayout.NORTH);
		getContentPane().add(caplenPane,BorderLayout.CENTER);
                getContentPane().add(buttonPane,BorderLayout.SOUTH);
		pack();

		setLocation(parent.getLocation().x+100,parent.getLocation().y+100);
	}

	public void actionPerformed(ActionEvent evt){
		String cmd=evt.getActionCommand();

		if(cmd.equals("Whole")){
			caplenField.setText("2048");
			caplenField.setEnabled(false);
		}else if(cmd.equals("Head")){
			caplenField.setText("68");
			caplenField.setEnabled(false);
		}else if(cmd.equals("Other")){
			caplenField.setText("");
			caplenField.setEnabled(true);
			caplenField.requestFocus();
		}else if(cmd.equals("OK")){
			try{
				int caplen=Integer.parseInt(caplenField.getText());
				if(caplen<68 || caplen>2048){
					JOptionPane.showMessageDialog(null,"���ݰ�����Ӧ��68�ֽں�2048�ֽ�֮��");
					return;
				}

				jpcap=Jpcap.openDevice(devices[adapterComboBox.getSelectedIndex()],caplen,
						promiscCheck.isSelected(),50);

				if(filterField.getText()!=null && filterField.getText().length()>0){
					jpcap.setFilter(filterField.getText(),true);
				}
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(null,"������Ϸ������ݰ�����");
			}catch(java.io.IOException e){
				JOptionPane.showMessageDialog(null,e.toString());
				jpcap=null;
			}finally{
				dispose();
			}
		}else if(cmd.equals("Cancel")){
                         dispose();
		}
	}

	public static Jpcap getJpcap(JDFrame parent){
		new JDCaptureDialog(parent).setVisible(true);
		return jpcap;
	}
}
