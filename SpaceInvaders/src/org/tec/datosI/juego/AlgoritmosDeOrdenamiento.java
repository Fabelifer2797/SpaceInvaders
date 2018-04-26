package org.tec.datosI.juego;

public class AlgoritmosDeOrdenamiento {
	
	int i,j, temporal;
	
	public AlgoritmosDeOrdenamiento()
	{
		this.i = 0;
		this.j = 0;
		this.temporal = 0;
	}
	
	public int[] BubbleSort(int Arreglo[])
	{
		for(i = 0; i < Arreglo.length; i++)
		{
			for(j = i + 1; j < Arreglo.length; j++)
			{
				if(Arreglo[i] > Arreglo[j])
				{
					temporal = Arreglo[i];
					Arreglo[i] = Arreglo[j];
					Arreglo[j] = temporal;
				}
			}
		}
		//this.MostrarArreglo(Arreglo);
		return Arreglo;
	}
	
	public void MostrarArreglo(int Arreglo[]) {
		int k;
		System.out.println("");
		
		for(k = 0; k < Arreglo.length; k++)
		{
			System.out.print("[" + Arreglo[k] + "]");
		}
		
	}
	
	public static void main(String[] args) {
		
		AlgoritmosDeOrdenamiento  Prueba = new  AlgoritmosDeOrdenamiento();
		int Arreglo[] = new int[5];
		Arreglo[0] = 3;
		Arreglo[1] = 1;
		Arreglo[2] = 20;
		Arreglo[3] = 238;
		Arreglo[4] = 20;
//		int Arreglo[] = {3,4,56,1,5,6,8,21,7};
		Prueba.BubbleSort(Arreglo);
		
	}

}
