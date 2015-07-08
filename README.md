# PhoneBillApp_Summer2015
Phone Bill Application for CS410J


Introduction
------------

Welcome to the Phone Bill Application! This is a command-line
application that allows the user to model a phone bill. In its
current version, the user may associate at most one phone record
per customer name. However, the information will not be stored
between each usage. A single phone record consists of the phone
number of the caller, the phone number that was called, the time
at which the call began, and the time at which the call ended.

Commands
--------

-README		The project description. Entering this option at
		the command line will display this page.
-print		A description of some phone call. Entering this
		option at the command line will display the
		description of the most recently added phone call.

To add a calling event, the following arguments must be provided
in the order listed below, separated by a single white space.

<customer>		Person whose phone bill we're modelling
<caller number>		Phone number of the caller
<callee number>		Phone number of the person called
<start time>		Date and time the call began
<end time>		Date and time the call ended

If the customer name contains more than one word, it must be
delimited by double quotes. Phone numbers must be of the form
nnn-nnn-nnnn where n is a number 0-9. Date and time should be
in the format: mm/dd/yyyy hh:mm and zeros may be omitted where
appropriate.

usage: java edu.pdx.cs410J.kathtran.Project1 [options] <args>

----------------------------------------------------------
CS410J PROJECT 1: DESIGNING A PHONE BILL APPLICATION

AUTHOR: KATHLEEN TRAN
LAST MODIFIED: 7/7/2015
