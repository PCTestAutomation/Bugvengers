		
# Performance Test Automation

		Parameterization and Dynamic testing
		Tool used: Apache JMeter and Jenkins (Open Source)
		
#		Test Config
       Used  Jmeter 5.4
       1 user will execute 100 times
	   JMX name QAworldcupPerformanceRound3.jmx
	   CSV name Numbers.csv
	   We used If controller to define logic for odd and even
	   Created aggregate and viewresult tree report
	   We are aslo printing the value of variable X
	   Add Backendlistner for influx and grafana if needs to be exeuted. Currently We have disabled it