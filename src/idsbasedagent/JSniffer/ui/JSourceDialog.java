package JSniffer.ui;
import jpcap.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
public class JSourceDialog extends JDialog implements ActionListener
{
         JTextField jtf1,jtf2,jtf3,jtf4;
         public  JDFrame frame;

         public static boolean suspendflag=false;

        public JSourceDialog( JDFrame parent){
           super(parent,"对象设置窗口",true);
          this.frame=parent;
           frame.captor.suspendCaptorThread();
           JLabel jlb=new JLabel("Source IP ");

           JButton jbtn=new JButton("确定");
           jbtn.setActionCommand("ok");
           jbtn.addActionListener(this);
           jtf1=new JTextField(3);
           jtf1.getDocument().addDocumentListener(d1);
           jtf2=new JTextField(3);
           jtf2.getDocument().addDocumentListener(d2);
           jtf3=new JTextField(3);
           jtf3.getDocument().addDocumentListener(d3);
           jtf4=new JTextField(3);
           jtf4.getDocument().addDocumentListener(d4);

           JLabel jlb1,jlb2,jlb3,jlb4,jlb5;
           jlb1=new JLabel(".");
           jlb2=new JLabel(".");
           jlb3=new JLabel(".");

           JPanel ipPane=new JPanel();
           ipPane.setLayout(new FlowLayout());
           ipPane.add(jtf1);
           ipPane.add(jlb1);
           ipPane.add(jtf2);
           ipPane.add(jlb2);
           ipPane.add(jtf3);
           ipPane.add(jlb3);
           ipPane.add(jtf4);

           getContentPane().setLayout(new FlowLayout());
           getContentPane().add(ipPane);

           getContentPane().add(jbtn);

           setSize(380,70);
           setLocation(200,150);

           addWindowListener(new WindowAdapter(){
                        public void windowClosing(WindowEvent evt){
                             frame.startUpdating();
                             frame.captor.continueCaptorThread();
                        }
                });


        }

        public String getIP()
        {
          return null;
        }
//////////////////////////////////////////////////////////////////////////////////////
         DocumentListener d1=new DocumentListener()
         {
           public void changedUpdate(DocumentEvent e)
           {

           }
           public void insertUpdate(DocumentEvent e){
             if(jtf1.getText().length()==3)
             {
                 int ip=Integer.parseInt(jtf1.getText());
                 if(ip>255||ip<0)
                   {
                     JOptionPane.showMessageDialog(null, "请输入合法的IP地址");
                   }
             }
             else if(jtf1.getText().length()>3)
                    {
                      JOptionPane.showMessageDialog(null, "请输入合法的IP地址");
                    }
           }
           public void removeUpdate(DocumentEvent e){}
         };
/////////////////////////////////////////////////////////////////////////////////////////////////
         DocumentListener d2=new DocumentListener()
         {
           public void changedUpdate(DocumentEvent e)
           {

           }
           public void insertUpdate(DocumentEvent e){
             if(jtf2.getText().length()==3)
             {
                 int ip=Integer.parseInt(jtf2.getText());
                 if(ip>255||ip<0)
                   {
                     JOptionPane.showMessageDialog(null, "请输入合法的IP地址");

                   }

             }
             else if(jtf2.getText().length()>3)
                   {
                     JOptionPane.showMessageDialog(null, "请输入合法的IP地址");

                   }
           }
           public void removeUpdate(DocumentEvent e){}
         };
//////////////////////////////////////////////////////////////////////////////////////////////
         DocumentListener d3=new DocumentListener()
        {
          public void changedUpdate(DocumentEvent e)
          {

          }
          public void insertUpdate(DocumentEvent e){
            if(jtf3.getText().length()==3)
            {
                int ip=Integer.parseInt(jtf3.getText());
                if(ip>255||ip<0)
                  {
                    JOptionPane.showMessageDialog(null, "请输入合法的IP地址");

                  }
            }
            else if(jtf3.getText().length()>3)
                   {
                     JOptionPane.showMessageDialog(null, "请输入合法的IP地址");

                   }
          }
          public void removeUpdate(DocumentEvent e){}
        };
///////////////////////////////////////////////////////////////////////////////////////////
        DocumentListener d4=new DocumentListener()
         {
           public void changedUpdate(DocumentEvent e)
           {

           }
           public void insertUpdate(DocumentEvent e){
             if(jtf4.getText().length()==3)
             {
                 int ip=Integer.parseInt(jtf4.getText());
                 if(ip>255||ip<0)
                   {
                     JOptionPane.showMessageDialog(null, "请输入合法的IP地址");

                   }

             }
             else if(jtf4.getText().length()>3)
                    {
                      JOptionPane.showMessageDialog(null, "请输入合法的IP地址");

                    }
           }
           public void removeUpdate(DocumentEvent e){}
         };
//////////////////////////////////////////////////////////////////////////////////////////
        public void actionPerformed(ActionEvent e){
                String cmd=e.getActionCommand();
              if(cmd.equals("ok"))
                {
                  String s1,s2,s3,s4;
                  s1=jtf1.getText();
                  s2=jtf2.getText();
                  s3=jtf3.getText();
                  s4=jtf4.getText();
                int n1,n2,n3,n4;
                  n1=Integer.parseInt(s1);
                  n2=Integer.parseInt(s2);
                  n3=Integer.parseInt(s3);
                  n4=Integer.parseInt(s4);

                 if((s1.length()>0 &&  s1.length()<=3)&&( n1>=0 && n1<=255) && (s2.length()>0 && s2.length()<=3) && ( n2>=0 && n2<=255 ) && (s3.length()>0 && s3.length()<=3) && (n3>=0 && n3<=255) && (s4.length()>0 && s4.length()<=3 ) && (n4>=0 && n4<=255))
                   {
                     String ip = jtf1.getText() + "." + jtf2.getText() + "." + jtf3.getText() +
                         "." + jtf4.getText();
                     this.frame.objstatus.setText("指定对象监听");
                     this.frame.ipstatus.setText(ip);
                     this.frame.stopobjButton.setEnabled(true);
                     this.frame.stopobjMenu.setEnabled(true);
                     this.frame.captor.packetClear();
                     this.frame.captor.setObjIP(ip);
                     this.frame.captor.setObjFlag(true);
                     this.frame.captor.continueCaptorThread();
                     dispose();
                   }
                   else
                     {
                       JOptionPane.showMessageDialog(null, "请输入合法的IP地址");
                     }
                }
                else
                  {
                    this.frame.captor.setObjFlag(false);
                    this.frame.startUpdating();
                    this.frame.objButton.setEnabled(true);
                    this.frame.objMenu.setEnabled(true);
                    this.frame.stopobjButton.setEnabled(false);
                    this.frame.stopobjMenu.setEnabled(false);
                    this.frame.captor.continueCaptorThread();
                  }
        }
}
