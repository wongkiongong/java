package com.onecloud.src;

class  TestTemplate
{
	public static void main(String[] args) 
	{
		//vector<T> v=new vector<T>(); 
		Byte b=new Byte((byte)0);
		vector<Byte> v=new vector<Byte>(b);
		v.add((byte)0);
		v.add((byte)1);
		v.add((byte)3);
		v.add((byte)2);
		v.visitAll();
		
		String s = "S";
		vector<String> vs = new vector<String>(s);
		vs.add("abc");
		vs.add("def");
		vs.add("ghi");
		vs.add("jkl");
		vs.visitAll();
		
	}
}

class vector<T>{

	private int size=0;
	private vectorElement<T> head=null;
	private vectorElement<T> last=null;

	public vector(T datum)
	{
		System.out.println("vector(T datum)!");
		this.head=new vectorElement<T>(datum);
		this.last=this.head;
		this.size=1; 
	}


public boolean add(T datum){

		if(this.size==0){
			this.head=new vectorElement<T>(datum);
			this.last=this.head;
			this.size=1;
		}else{
			vectorElement<T> temp=new vectorElement<T>(datum);
			temp.previous=this.last;
			this.last.next=temp;
			this.last=temp;
			this.size=this.size+1;
		}
		return true;

	}

	public void visitAll()
	{
		vectorElement<T> walker;
		walker=head;
		while(walker !=null)
		{
			System.out.println(walker.datum);
			walker=walker.next;
		}
	}
}

class vectorElement<T>{

	public vectorElement<T> next=null;
	public vectorElement<T> previous=null;
	public T datum=null;

	public vectorElement(T datum){ this.datum=datum; }

}

/*
vector(T datum)!
0
0
1
3
2
请按任意键继续. . .
*/
