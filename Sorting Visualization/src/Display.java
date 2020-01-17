import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;

public class Display extends Application
{	
	private double animationSpeed=5;
	private final int dataUp=400;
	private int dataNum=100;
	private double recWidth;
	
	private final Color backColor1=Color.rgb(0,0,0,0);
	private final Color backColor2=Color.rgb(255,127,127,0.75);
	
	private int[] data;
	private Rectangle[] back;
	private Rectangle[] front;
	private Button btSO=new Button("Ascending Order");
	private Button btOD=new Button("Generate Equal-difference Array");
	private Button btRD=new Button("Generate Random Array");
	private Button btSH=new Button("Shuffle");
	private Button btBS=new Button("Bubble Sort");
	private Button btSS=new Button("Selection Sort");
	private Button btIS=new Button("Insertion Sort");
	private Button btSHS=new Button("Shell Sort");
	private Button btQS=new Button("Quick Sort");
	private Button btMS=new Button("Merge Sort");
	private Button btAC=new Button("Pause");
	private Button btCA=new Button("Cancel");
	private Button btOP=new Button("Options");
	
	private boolean order=true;
	private Timeline animation=new Timeline();
	
	private void graphInit(Stage primaryStage)
	{
		BorderPane background=new BorderPane();		
		Group recs=new Group();
		
		recWidth=800.0/dataNum;
		data=new int[dataNum];
		back=new Rectangle[dataNum];
		front=new Rectangle[dataNum];
		for (int i=0;i<dataNum;i++)
		{
			data[i]=0;
			back[i]=new Rectangle(recWidth*i,-0.1*dataUp,recWidth,1.2*dataUp);
			front[i]=new Rectangle(recWidth*i,dataUp-data[i],recWidth,data[i]);
		}
		shuffle();
		for (int i=0;i<dataNum;i++)
		{
			recs.getChildren().add(front[i]);
			recs.getChildren().add(back[i]);
		}
		HBox bottom1=new HBox();
		HBox bottom2=new HBox();
		VBox bottomBts=new VBox(bottom1,bottom2);
		VBox leftBts=new VBox();
		VBox rightBts=new VBox();
		btSO.setOnAction(e->{
			if (btSO.getText()=="Ascending Order")
			{
				btSO.setText("Descending Order");
				order=false;
			}
			else
			{
				btSO.setText("Ascending Order");
				order=true;
			}
		});
		btOD.setOnAction(e->{
			generateOrder();
		});
		btRD.setOnAction(e->{
			generateRandom();
		});
		btSH.setOnAction(e->{
			shuffle();
		});
		btBS.setOnAction(e->{
			bubbleSort(order);
			animation.play();
			setButtonsDisable(true);
		});
		btSS.setOnAction(e->{
			selectionSort(order);
			animation.play();
			setButtonsDisable(true);
		});
		btIS.setOnAction(e->{
			insertionSort(order);
			animation.play();
			setButtonsDisable(true);
		});
		btSHS.setOnAction(e->{
			shellSort(order);
			animation.play();
			setButtonsDisable(true);
		});
		btQS.setOnAction(e->{
			quickSort(order,0,0,dataNum-1);
			animation.play();
			setButtonsDisable(true);
		});
		btMS.setOnAction(e->{
			mergeSort(order,0,0,dataNum-1);
			animation.play();
			setButtonsDisable(true);
		});
		btAC.setDisable(true);
		btAC.setOnAction(e->{
			if (btAC.getText()=="Pause")
			{
				animation.pause();
				btAC.setText("Resume");
				btCA.setDisable(false);
			}
			else
			{
				animation.play();
				btAC.setText("Pause");
				btCA.setDisable(true);
			}
		});
		btCA.setDisable(true);
		btCA.setOnAction(e->{
			animation.stop();
			animation.getKeyFrames().clear();
			btAC.setText("Pause");
			setButtonsDisable(false);
			for (int i=0;i<dataNum;i++)
			{
				data[i]=(int)front[i].getHeight();
				back[i].setFill(backColor1);
			}
			btCA.setDisable(true);
		});
		btOP.setOnAction(e->{
			optionStageInit(primaryStage);
		});
		bottom1.getChildren().addAll(btSO,btOD,btRD,btSH);
		bottom2.getChildren().addAll(btBS,btSS,btIS,btSHS,btQS,btMS);
		leftBts.getChildren().addAll(btAC,btCA);
		rightBts.getChildren().addAll(btOP);
		background.setCenter(recs);
		background.setBottom(bottomBts);
		background.setLeft(leftBts);
		background.setRight(rightBts);
		Scene scene=new Scene(background,1000,720);
		scene.getStylesheets().add("./css/stylesheet.css");
		for (int i=0;i<dataNum;i++)
		{
			back[i].getStyleClass().add("rectangle_back");
			front[i].getStyleClass().add("rectangle_front");
		}
		primaryStage.setTitle("Sorting Visualization");
		primaryStage.setScene(scene);
		//primaryStage.setFullScreen(true);
		primaryStage.show();
		animation.setOnFinished(e->{
			animation.getKeyFrames().clear();
			setButtonsDisable(false);
		});
	}
	
	private void optionStageInit(Stage primaryStage)
	{
		Label lbAS=new Label("Animation Speed");
		Label lbDN=new Label("Data Number");
		TextField tfAS=new TextField(Double.toString(animationSpeed));
		TextField tfDN=new TextField(Integer.toString(dataNum));
		Slider sldAS=new Slider(0,500,animationSpeed);
		Slider sldDN=new Slider(0,800,dataNum);
		Button btOK=new Button("OK");
		Button btCA=new Button("Cancel");
		
		Stage optionStage=new Stage();
		Pane background=new Pane();	
		Group options=new Group();
		VBox elements=new VBox();
		HBox bts=new HBox();
		bts.setAlignment(Pos.CENTER);
		bts.getChildren().addAll(btOK,btCA);
		elements.getChildren().addAll(lbAS,tfAS,sldAS,lbDN,tfDN,sldDN,bts);
		options.getChildren().addAll(elements);
		background.getChildren().addAll(options);
		options.setLayoutX(40);
		options.setLayoutY(20);
		Scene scene=new Scene(background,480,260);
		scene.getStylesheets().add("./css/stylesheet.css");
		optionStage.setTitle("Options");
		optionStage.setScene(scene);
		optionStage.setResizable(false);
		optionStage.show();
		
		tfAS.setOnAction(e -> {
			sldAS.setValue(Double.valueOf(tfAS.getText()));
        });
		sldAS.valueProperty().addListener(e -> {
			tfAS.setText(Double.toString(sldAS.getValue()));
        });
		
		tfDN.setOnAction(e -> {
			sldDN.setValue(Integer.valueOf(tfDN.getText()));
        });
		sldDN.valueProperty().addListener(e -> {
			tfDN.setText(Integer.toString((int)sldDN.getValue()));
        });
		
		btOK.setOnAction(e->{
			animationSpeed=Double.valueOf(tfAS.getText());
			dataNum=Integer.valueOf(tfDN.getText());
			primaryStage.close();
			graphInit(primaryStage);
			optionStage.close();
		});	
		
		btCA.setOnAction(e->{
			optionStage.close();
		});	
	}
	
	private void setButtonsDisable(boolean isDisabled)
	{
		btSO.setDisable(isDisabled);
		btOD.setDisable(isDisabled);
		btRD.setDisable(isDisabled);
		btSH.setDisable(isDisabled);
		btBS.setDisable(isDisabled);
		btSS.setDisable(isDisabled);
		btIS.setDisable(isDisabled);
		btSHS.setDisable(isDisabled);
		btQS.setDisable(isDisabled);
		btMS.setDisable(isDisabled);
		btAC.setDisable(!isDisabled);
		btOP.setDisable(isDisabled);
	}
	
	private void generateOrder()
	{
		if (order)
			for (int i=0;i<dataNum;i++)
			{
				data[i]=(i+1)*dataUp/dataNum;
				front[i].setY(dataUp-data[i]);
				front[i].setHeight(data[i]);
			}
		else
			for (int i=0;i<dataNum;i++)
			{
				data[i]=(dataNum-i)*dataUp/dataNum;
				front[i].setY(dataUp-data[i]);
				front[i].setHeight(data[i]);
			}
	}
	
	private void generateRandom()
	{
		for (int i=0;i<dataNum;i++)
		{
			data[i]=(int)(Math.random()*dataUp)+1;
			front[i].setY(dataUp-data[i]);
			front[i].setHeight(data[i]);
		}
	}
	
	private void shuffle()
	{
		int temp,p;
		for (int i=dataNum-1;i>=0;i--)
		{
			p=(int)(Math.random()*i);
			temp=data[i];
			data[i]=data[p];
			data[p]=temp;
			front[i].setY(dataUp-data[i]);
			front[i].setHeight(data[i]);
		}
	}
	
	private void bubbleSort(boolean isAscending)
	{
		int c=0;
		if(isAscending)
		{
			for (int i=0;i<dataNum;i++)
				for (int j=dataNum-1;j>i;j--)
				{
					c++;
					if (data[j]<data[j-1])
					{
						int temp=data[j];
						data[j]=data[j-1];
						data[j-1]=temp;
						final int _j=j,_c=c;
						animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
							int _temp1=(int)front[_j].getHeight();
							int _temp2=(int)front[_j].getY();
							front[_j].setHeight(front[_j-1].getHeight());
							front[_j].setY(front[_j-1].getY());
							front[_j-1].setHeight(_temp1);
							front[_j-1].setY(_temp2);
							back[_j].setFill(backColor2);
							back[_j-1].setFill(backColor2);
						}));
						animation.getKeyFrames().add(new KeyFrame(Duration.millis((_c+1)*animationSpeed),e->{
							back[_j].setFill(backColor1);
							back[_j-1].setFill(backColor1);
						}));
					}
					else
					{
						final int _j=j,_c=c;
						animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
							back[_j].setFill(backColor2);		
						}));
						animation.getKeyFrames().add(new KeyFrame(Duration.millis((_c+1)*animationSpeed),e->{
							back[_j].setFill(backColor1);		
						}));
					}
				}
		}
		else
		{
			for (int i=0;i<dataNum;i++)
				for (int j=dataNum-1;j>i;j--)
				{
					c++;
					if (data[j]>data[j-1])
					{
						int temp=data[j];
						data[j]=data[j-1];
						data[j-1]=temp;
						final int _j=j,_c=c;
						animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
							int _temp1=(int)front[_j].getHeight();
							int _temp2=(int)front[_j].getY();
							front[_j].setHeight(front[_j-1].getHeight());
							front[_j].setY(front[_j-1].getY());
							front[_j-1].setHeight(_temp1);
							front[_j-1].setY(_temp2);
							back[_j].setFill(backColor2);
							back[_j-1].setFill(backColor2);
						}));
						animation.getKeyFrames().add(new KeyFrame(Duration.millis((_c+1)*animationSpeed),e->{
							back[_j].setFill(backColor1);
							back[_j-1].setFill(backColor1);
						}));
					}
					else
					{
						final int _j=j,_c=c;
						animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
							back[_j].setFill(backColor2);		
						}));
						animation.getKeyFrames().add(new KeyFrame(Duration.millis((_c+1)*animationSpeed),e->{
							back[_j].setFill(backColor1);		
						}));
					}
				}
		}
	}
	
	private void selectionSort(boolean isAscending)
	{
		int c=0,p;
		if(isAscending)
		{
			for (int i=0;i<dataNum;i++)
			{
				p=i;
				final int _c=c,_p=p;
				animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
					back[_p].setFill(backColor2);
				}));
				for (int j=i+1;j<dataNum;j++)
				{
					c++;
					final int __j=j,__c=c;
					animation.getKeyFrames().add(new KeyFrame(Duration.millis(__c*animationSpeed),f->{
						back[__j].setFill(backColor2);
					}));
					if (data[j]<data[p])
					{
						final int __p=p;
						animation.getKeyFrames().add(new KeyFrame(Duration.millis((__c+1)*animationSpeed),f->{
							back[__p].setFill(backColor1);
						}));
						p=j;
					}
					else
					{
						final int _j=j;
						animation.getKeyFrames().add(new KeyFrame(Duration.millis((__c+1)*animationSpeed),f->{
							back[_j].setFill(backColor1);
						}));
					}
				}
				c++;
				int temp=data[i];
				data[i]=data[p];
				data[p]=temp;
				final int __c=c,__i=i,__p=p;
				animation.getKeyFrames().add(new KeyFrame(Duration.millis(__c*animationSpeed),e->{
					int _temp1=(int)front[__i].getHeight();
					int _temp2=(int)front[__i].getY();
					front[__i].setHeight(front[__p].getHeight());
					front[__i].setY(front[__p].getY());
					front[__p].setHeight(_temp1);
					front[__p].setY(_temp2);
					back[__i].setFill(backColor2);
					back[__p].setFill(backColor2);
				}));
				animation.getKeyFrames().add(new KeyFrame(Duration.millis((__c+2)*animationSpeed),e->{
					back[__i].setFill(backColor1);
					back[__p].setFill(backColor1);
				}));
				c+=3;
			}
		}
		else
		{
			for (int i=0;i<dataNum;i++)
			{
				p=i;
				final int _c=c,_p=p;
				animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
					back[_p].setFill(backColor2);
				}));
				for (int j=i+1;j<dataNum;j++)
				{
					c++;
					final int __j=j,__c=c;
					animation.getKeyFrames().add(new KeyFrame(Duration.millis(__c*animationSpeed),f->{
						back[__j].setFill(backColor2);
					}));
					if (data[j]>data[p])
					{
						final int __p=p;
						animation.getKeyFrames().add(new KeyFrame(Duration.millis((__c+1)*animationSpeed),f->{
							back[__p].setFill(backColor1);
						}));
						p=j;
					}
					else
					{
						final int _j=j;
						animation.getKeyFrames().add(new KeyFrame(Duration.millis((__c+1)*animationSpeed),f->{
							back[_j].setFill(backColor1);
						}));
					}
				}
				c++;
				int temp=data[i];
				data[i]=data[p];
				data[p]=temp;
				final int __c=c,__i=i,__p=p;
				animation.getKeyFrames().add(new KeyFrame(Duration.millis(__c*animationSpeed),e->{
					int _temp1=(int)front[__i].getHeight();
					int _temp2=(int)front[__i].getY();
					front[__i].setHeight(front[__p].getHeight());
					front[__i].setY(front[__p].getY());
					front[__p].setHeight(_temp1);
					front[__p].setY(_temp2);
					back[__i].setFill(backColor2);
					back[__p].setFill(backColor2);
				}));
				animation.getKeyFrames().add(new KeyFrame(Duration.millis((__c+2)*animationSpeed),e->{
					back[__i].setFill(backColor1);
					back[__p].setFill(backColor1);
				}));
				c+=3;
			}
		}
	}
	
	private void insertionSort(boolean isAscending)
	{
		int c=0,temp;
		if(isAscending)
		{
			for (int i=0;i<dataNum;i++)
			{
				temp=data[i];
				int j;
				for (j=i;j>0;j--)
				{
					c++;
					final int _c=c,_j=j;
					animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
						back[_j].setFill(backColor2);
					}));
					animation.getKeyFrames().add(new KeyFrame(Duration.millis((_c+1)*animationSpeed),e->{
						back[_j].setFill(backColor1);
					}));
					if (temp>=data[j-1])
						break;
					else
					{
						data[j]=data[j-1];
						animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
							front[_j].setHeight(front[_j-1].getHeight());
							front[_j].setY(front[_j-1].getY());
						}));
					}		
				}
				c++;
				final int _c=c,_j=j,_temp=temp;
				data[j]=temp;
				animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
					front[_j].setHeight(_temp);
					front[_j].setY(dataUp-_temp);
					back[_j].setFill(backColor2);
				}));
				animation.getKeyFrames().add(new KeyFrame(Duration.millis((_c+1)*animationSpeed),e->{
					back[_j].setFill(backColor1);
				}));
			}
		}
		else
		{
			for (int i=0;i<dataNum;i++)
			{
				temp=data[i];
				int j;
				for (j=i;j>0;j--)
				{
					c++;
					final int _c=c,_j=j;
					animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
						back[_j].setFill(backColor2);
					}));
					animation.getKeyFrames().add(new KeyFrame(Duration.millis((_c+1)*animationSpeed),e->{
						back[_j].setFill(backColor1);
					}));
					if (temp<=data[j-1])
						break;
					else
					{
						data[j]=data[j-1];
						animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
							front[_j].setHeight(front[_j-1].getHeight());
							front[_j].setY(front[_j-1].getY());
						}));
					}		
				}
				c++;
				final int _c=c,_j=j,_temp=temp;
				data[j]=temp;
				animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
					front[_j].setHeight(_temp);
					front[_j].setY(dataUp-_temp);
					back[_j].setFill(backColor2);
				}));
				animation.getKeyFrames().add(new KeyFrame(Duration.millis((_c+1)*animationSpeed),e->{
					back[_j].setFill(backColor1);
				}));
			}
		}
	}
	
	private void shellSort(boolean isAscending)
	{
		int c=0,temp;
		if (isAscending)
		{
			for (int gap=dataNum/3;gap!=0;gap/=3)
			{
				for (int i=0;i<gap;i++)
					for (int j=i;j<dataNum;j+=gap)
					{
						temp=data[j];
						int k;
						for (k=j;k>i;k-=gap)
						{
							c++;
							final int _c=c,_k=k,_gap=gap;
							animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
								back[_k].setFill(backColor2);
							}));
							animation.getKeyFrames().add(new KeyFrame(Duration.millis((_c+1)*animationSpeed),e->{
								back[_k].setFill(backColor1);
							}));
							if (temp>=data[k-gap])
							{
								break;
							}
							else
							{
								data[k]=data[k-gap];
								animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
									front[_k].setHeight(front[_k-_gap].getHeight());
									front[_k].setY(front[_k-_gap].getY());
								}));
							}
						}
						data[k]=temp;
						c++;
						final int _c=c,_k=k,_temp=temp;
						animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
							front[_k].setHeight(_temp);
							front[_k].setY(dataUp-_temp);
							back[_k].setFill(backColor2);
						}));
						animation.getKeyFrames().add(new KeyFrame(Duration.millis((_c+1)*animationSpeed),e->{
							back[_k].setFill(backColor1);
						}));
					}
				if (gap==2) gap=3;
			}
		}
		else
		{
			for (int gap=dataNum/3;gap!=0;gap/=3)
			{
				for (int i=0;i<gap;i++)
					for (int j=i;j<dataNum;j+=gap)
					{
						temp=data[j];
						int k;
						for (k=j;k>i;k-=gap)
						{
							c++;
							final int _c=c,_k=k,_gap=gap;
							animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
								back[_k].setFill(backColor2);
							}));
							animation.getKeyFrames().add(new KeyFrame(Duration.millis((_c+1)*animationSpeed),e->{
								back[_k].setFill(backColor1);
							}));
							if (temp<=data[k-gap])
							{
								break;
							}
							else
							{
								data[k]=data[k-gap];
								animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
									front[_k].setHeight(front[_k-_gap].getHeight());
									front[_k].setY(front[_k-_gap].getY());
								}));
							}
						}
						data[k]=temp;
						c++;
						final int _c=c,_k=k,_temp=temp;
						animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
							front[_k].setHeight(_temp);
							front[_k].setY(dataUp-_temp);
						}));
					}
				if (gap==2) gap=3;
			}
		}
	}
	
	private int quickSort(boolean isAscending,int c,int down,int up)
	{
		if (down>=up) return c;
		int temp=data[down];
		if (isAscending)
		{
			int low=down,high=up;
			final int _low=low,_high=high,_c=c;
			animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
				back[_low].setFill(backColor2);
				back[_high].setFill(backColor2);
			}));
			while (low!=high)
			{
				while(data[high]>=temp && low!=high)
				{
					c++;
					high--;
					final int __high=high,__c=c;
					animation.getKeyFrames().add(new KeyFrame(Duration.millis(__c*animationSpeed),e->{
						back[__high+1].setFill(backColor1);
						back[__high].setFill(backColor2);
					}));
				}
				data[low]=data[high];
				c++;
				final int ___high=high,___low=low,___c=c;
				animation.getKeyFrames().add(new KeyFrame(Duration.millis(___c*animationSpeed),e->{
					front[___low].setHeight(front[___high].getHeight());
					front[___low].setY(front[___high].getY());
				}));
				while (data[low]<=temp && low!=high)
				{
					c++;
					low++;
					final int __low=low,__c=c;
					animation.getKeyFrames().add(new KeyFrame(Duration.millis(__c*animationSpeed),e->{
						back[__low-1].setFill(backColor1);
						back[__low].setFill(backColor2);
					}));
				}
				data[high]=data[low];
				c++;
				final int ____high=high,____low=low,____c=c;
				animation.getKeyFrames().add(new KeyFrame(Duration.millis(____c*animationSpeed),e->{
					front[____high].setHeight(front[____low].getHeight());
					front[____high].setY(front[____low].getY());
				}));
			}
			c++;
			final int __low=low,__c=c;
			animation.getKeyFrames().add(new KeyFrame(Duration.millis(__c*animationSpeed),e->{
				front[__low].setHeight(temp);
				front[__low].setY(dataUp-temp);
			}));
			animation.getKeyFrames().add(new KeyFrame(Duration.millis((__c+1)*animationSpeed),e->{
				back[__low].setFill(backColor1);
			}));
			data[low]=temp;
			c++;
			c=quickSort(true,c,down,low-1);
			c=quickSort(true,c,low+1,up);
		}
		else
		{
			int low=up,high=down;
			final int _low=low,_high=high,_c=c;
			animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
				back[_low].setFill(backColor2);
				back[_high].setFill(backColor2);
			}));
			while (low!=high)
			{
				while (data[low]<=temp && low!=high)
				{
					c++;
					low--;
					final int __low=low,__c=c;
					animation.getKeyFrames().add(new KeyFrame(Duration.millis(__c*animationSpeed),e->{
						back[__low+1].setFill(backColor1);
						back[__low].setFill(backColor2);
					}));
				}
				data[high]=data[low];
				c++;
				final int ____high=high,____low=low,____c=c;
				animation.getKeyFrames().add(new KeyFrame(Duration.millis(____c*animationSpeed),e->{
					front[____high].setHeight(front[____low].getHeight());
					front[____high].setY(front[____low].getY());
				}));
				while(data[high]>=temp && low!=high)
				{
					c++;
					high++;
					final int __high=high,__c=c;
					animation.getKeyFrames().add(new KeyFrame(Duration.millis(__c*animationSpeed),e->{
						back[__high-1].setFill(backColor1);
						back[__high].setFill(backColor2);
					}));
				}
				data[low]=data[high];
				c++;
				final int ___high=high,___low=low,___c=c;
				animation.getKeyFrames().add(new KeyFrame(Duration.millis(___c*animationSpeed),e->{
					front[___low].setHeight(front[___high].getHeight());
					front[___low].setY(front[___high].getY());
				}));
			}
			c++;
			final int __low=low,__c=c;
			animation.getKeyFrames().add(new KeyFrame(Duration.millis(__c*animationSpeed),e->{
				front[__low].setHeight(temp);
				front[__low].setY(dataUp-temp);
			}));
			animation.getKeyFrames().add(new KeyFrame(Duration.millis((__c+1)*animationSpeed),e->{
				back[__low].setFill(backColor1);
			}));
			data[low]=temp;
			c++;
			c=quickSort(false,c,down,low-1);
			c=quickSort(false,c,low+1,up);
		}
		return c;
	}
	
	private int mergeSort(boolean isAscending,int c,int down,int up)
	{
		if (down==up) return c;
		int[] temp=new int[up-down+1];
		int p1,p2,p;
		if (isAscending)
		{
			c=mergeSort(true,c,down,(down+up)/2);
			c=mergeSort(true,c,(down+up)/2+1,up);
			for (int i=down;i<=up;i++)
				temp[i-down]=data[i];
			p1=down;p2=(down+up)/2+1;p=down;
			while (p1<=(down+up)/2 && p2<=up)
			{
				if (temp[p1-down]<temp[p2-down])
				{
					c++;
					data[p]=temp[p1-down];
					final int _c=c,_p=p,_p1=p1;
					animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
						front[_p].setHeight(temp[_p1-down]);
						front[_p].setY(dataUp-temp[_p1-down]);
						back[_p].setFill(backColor2);
					}));
					animation.getKeyFrames().add(new KeyFrame(Duration.millis((_c+1)*animationSpeed),e->{
						back[_p].setFill(backColor1);
					}));
					p++;p1++;
				}
				else
				{
					c++;
					data[p]=temp[p2-down];
					final int _c=c,_p=p,_p2=p2;
					animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
						front[_p].setHeight(temp[_p2-down]);
						front[_p].setY(dataUp-temp[_p2-down]);
						back[_p].setFill(backColor2);
					}));
					animation.getKeyFrames().add(new KeyFrame(Duration.millis((_c+1)*animationSpeed),e->{
						back[_p].setFill(backColor1);
					}));
					p++;p2++;
				}
			}
			if (p1>(down+up)/2)
				while (p2<=up)
				{
					c++;
					data[p]=temp[p2-down];
					final int _c=c,_p=p,_p2=p2;
					animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
						front[_p].setHeight(temp[_p2-down]);
						front[_p].setY(dataUp-temp[_p2-down]);
						back[_p].setFill(backColor2);
					}));
					animation.getKeyFrames().add(new KeyFrame(Duration.millis((_c+1)*animationSpeed),e->{
						back[_p].setFill(backColor1);
					}));
					p++;p2++;
				}
			else
			{
				while (p1<=(down+up)/2)
				{
					c++;
					data[p]=temp[p1-down];
					final int _c=c,_p=p,_p1=p1;
					animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
						front[_p].setHeight(temp[_p1-down]);
						front[_p].setY(dataUp-temp[_p1-down]);
						back[_p].setFill(backColor2);
					}));
					animation.getKeyFrames().add(new KeyFrame(Duration.millis((_c+1)*animationSpeed),e->{
						back[_p].setFill(backColor1);
					}));
					p++;p1++;
				}
			}
		}
		else
		{
			c=mergeSort(false,c,down,(down+up)/2);
			c=mergeSort(false,c,(down+up)/2+1,up);
			for (int i=down;i<=up;i++)
				temp[i-down]=data[i];
			p1=down;p2=(down+up)/2+1;p=down;
			while (p1<=(down+up)/2 && p2<=up)
			{
				if (temp[p1-down]>temp[p2-down])
				{
					c++;
					data[p]=temp[p1-down];
					final int _c=c,_p=p,_p1=p1;
					animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
						front[_p].setHeight(temp[_p1-down]);
						front[_p].setY(dataUp-temp[_p1-down]);
						back[_p].setFill(backColor2);
					}));
					animation.getKeyFrames().add(new KeyFrame(Duration.millis((_c+1)*animationSpeed),e->{
						back[_p].setFill(backColor1);
					}));
					p++;p1++;
				}
				else
				{
					c++;
					data[p]=temp[p2-down];
					final int _c=c,_p=p,_p2=p2;
					animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
						front[_p].setHeight(temp[_p2-down]);
						front[_p].setY(dataUp-temp[_p2-down]);
						back[_p].setFill(backColor2);
					}));
					animation.getKeyFrames().add(new KeyFrame(Duration.millis((_c+1)*animationSpeed),e->{
						back[_p].setFill(backColor1);
					}));
					p++;p2++;
				}
			}
			if (p1>(down+up)/2)
				while (p2<=up)
				{
					c++;
					data[p]=temp[p2-down];
					final int _c=c,_p=p,_p2=p2;
					animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
						front[_p].setHeight(temp[_p2-down]);
						front[_p].setY(dataUp-temp[_p2-down]);
						back[_p].setFill(backColor2);
					}));
					animation.getKeyFrames().add(new KeyFrame(Duration.millis((_c+1)*animationSpeed),e->{
						back[_p].setFill(backColor1);
					}));
					p++;p2++;
				}
			else
			{
				while (p1<=(down+up)/2)
				{
					c++;
					data[p]=temp[p1-down];
					final int _c=c,_p=p,_p1=p1;
					animation.getKeyFrames().add(new KeyFrame(Duration.millis(_c*animationSpeed),e->{
						front[_p].setHeight(temp[_p1-down]);
						front[_p].setY(dataUp-temp[_p1-down]);
						back[_p].setFill(backColor2);
					}));
					animation.getKeyFrames().add(new KeyFrame(Duration.millis((_c+1)*animationSpeed),e->{
						back[_p].setFill(backColor1);
					}));
					p++;p1++;
				}
			}
		}
		return c;
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		graphInit(primaryStage);
	}
	
	public static void main(String[] args)
	{
		Application.launch(args);
	}
}
