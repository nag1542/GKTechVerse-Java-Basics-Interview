package com.gktechverse.corejava.strings;


class Employee{
	int id;

	Employee(int id) {
        this.id = id;
    }	
	

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;

	    Employee emp = (Employee) obj;
	    return id == emp.id;
	}
	
}

public class EqualsWithCustomClass {
	public static void main(String[] args) {
		
		Employee e1 = new Employee(101);
        Employee e2 = new Employee(101);

        System.out.println("Employee object compare with equals = "+e1.equals(e2));

	}
}
