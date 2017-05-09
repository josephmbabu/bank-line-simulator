/**
* BankSim.java
* @author Joseph Mbabu
*
* Spring 2015
*/

import java.util.Random;

public class BankSim{
    static  LLPriQue<Integer> llPriQue = new LLPriQue<Integer>();

	static Random  random_generator = new Random();

    // headings
	static String heading1 = "Visit time";
	static String heading2 = "Visit length";
    static String heading3 = "Wait time";
    static String heading4 = "Servive time";

    static double avg_wait_time = 0.0; // average wait time
    static double avg_avg_time = 0.0; // average of the average wait time
    static int max_wait_time = 0;    // maximum wait time


	// generates customer arrival times and length of visits
	public static void time_of_visit(int num_of_visits, int day_length, int max_visitLength){
		for(int i = 1; i <= num_of_visits; i++){
			int visit_time = random_generator.nextInt(day_length - 1) + 1;
			int max_visit = random_generator.nextInt(max_visitLength-1) + 1;
			llPriQue.insert(visit_time,max_visit);
		}
	}

    public static void transaction(int visit_length, int[] tellers, int current_time){
    	int done_time = 0;  // time when customer in front is done
    	int service_time = 0; // time when customer is serviced

        if(llPriQue.isEmpty() == false){ //  if line is not empty
        	int wait_time = 0;  //  customer wait time

    		int arrival_time =  llPriQue.head.pri; // get customer arrival time

        	int transaction_time = llPriQue.head.data; // transaction time

		 	for(int i = 0; i< tellers.length; i++){
         		if( --tellers[i] <= 0){
         			if(current_time >= arrival_time){
            			tellers[i] = transaction_time + 1; // transaction time plus time to get ready for nexrt customer
            			wait_time = current_time - arrival_time; // wait time
            			service_time = arrival_time + wait_time; // servive time
            			avg_wait_time += wait_time; //

                        // find maximum wait time
            			if(wait_time > max_wait_time){
            				max_wait_time = wait_time;
            			}

            			llPriQue.remove(); // remove from queue

            			// display transaction info
            			System.out.printf("%-10s %5s %9s %10s %n\n",arrival_time, transaction_time, wait_time, service_time);
            		}
           		}
        	}
    	}

    }

    // main
	public static void main(String[] args){
		int num_of_trials = 0;    // number of simulation trials
		int day_length =0;        // length of bank day
		int visits = 0;           // number of customer visits
		int max_visitLength = 0;  // maximum length of a visit
		int teller = 0;           // number of tellers


		if(args.length != 5){ // validate input
			System.out.println("\n################# Bank Line Simulation ##################");
			System.out.print("\nEnter the following in the listed oreder:\nNumber of trials" +
				             "\nLength of bank day\nNumber of visits\nMax lenth of visit and \nNumber of tellers\n");

			System.exit(0); // terminate program
		}
		else
			System.out.println("\n################# Bank Line Simulation ##################\n");

		    // display heading
		    System.out.printf("%-10s %15s %20s %25s%n", heading1,heading2, heading3,heading4);

            try{
		    	num_of_trials = Integer.parseInt(args[0]);   // get number of trials
		   		day_length = Integer.parseInt(args[1]);      // get length of day
		    	visits = Integer.parseInt(args[2]);          // get number of visits
		    	max_visitLength = Integer.parseInt(args[3]); // get maximum length of any visit
		    	teller = Integer.parseInt(args[4]);         // get number of tellers
		    }
		    catch(NumberFormatException e){
		    	System.out.println("Wrong input. Enter digits only.");
		    }

		    // holds the number of tellers
		    int[] tellers = new int[teller];

            for(int i = 0; i< num_of_trials; i++){  // multiple trials
            	time_of_visit(visits, day_length,max_visitLength);

            	for(int j = 0; j< day_length; j++){
                	transaction(max_visitLength, tellers,j);
           		}

           		 avg_wait_time /= visits; // average wait time per customer

                 avg_avg_time += avg_wait_time; // average of average wait time

                 // display
            	 System.out.printf("Average wait time: %s\nMaximum wait time: %s\n", avg_wait_time,max_wait_time);
            }
            avg_avg_time /= num_of_trials;

            // display average for multiple trials
            System.out.printf("Average for multiple trials: %s\n", avg_avg_time);
	}
}
