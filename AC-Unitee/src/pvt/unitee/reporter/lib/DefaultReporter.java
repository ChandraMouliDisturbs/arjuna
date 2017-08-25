/*******************************************************************************
 * Copyright 2015-16 AutoCognite Testing Research Pvt Ltd
 * 
 * Website: www.AutoCognite.com
 * Email: support [at] autocognite.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package pvt.unitee.reporter.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import arjunasdk.config.RunConfig;
import pvt.batteries.config.Batteries;
import pvt.unitee.enums.ArjunaProperty;
import pvt.unitee.interfaces.InternlReportableObserver;
import pvt.unitee.reporter.lib.event.Event;
import pvt.unitee.reporter.lib.fixture.FixtureResult;
import pvt.unitee.reporter.lib.ignored.IgnoredTest;
import pvt.unitee.reporter.lib.issue.Issue;
import pvt.unitee.reporter.lib.step.StepResult;
import pvt.unitee.reporter.lib.test.TestResult;
import pvt.unitee.reporter.lib.writer.json.JsonEventWriter;
import pvt.unitee.reporter.lib.writer.json.JsonFixtureResultWriter;
import pvt.unitee.reporter.lib.writer.json.JsonIgnoredTestWriter;
import pvt.unitee.reporter.lib.writer.json.JsonIssueWriter;
import pvt.unitee.reporter.lib.writer.json.JsonTestResultWriter;

public class DefaultReporter implements Reporter{
	private static Logger logger = RunConfig.logger();
	private List<InternlReportableObserver<TestResult>> testResultObservers = new ArrayList<InternlReportableObserver<TestResult>>();
	private List<InternlReportableObserver<IgnoredTest>> ignoredTestObservers = new ArrayList<InternlReportableObserver<IgnoredTest>>();
	private List<InternlReportableObserver<StepResult>> stepResultObservers = new ArrayList<InternlReportableObserver<StepResult>>();
	private List<InternlReportableObserver<Issue>> issueObservers = new ArrayList<InternlReportableObserver<Issue>>();
	private List<InternlReportableObserver<Event>> eventObservers = new ArrayList<InternlReportableObserver<Event>>();
	private List<InternlReportableObserver<FixtureResult>> fixtureResultObservers = new ArrayList<InternlReportableObserver<FixtureResult>>();
	
	private String getRunIDReportDir() throws Exception{
		return Batteries.value(ArjunaProperty.DIRECTORY_PROJECT_RUNID_REPORT_ROOT).asString();
	}
	
	public DefaultReporter() throws Exception{
		SummaryResult.init();
		FileUtils.forceMkdir(new File(getRunIDReportDir()));	
		String reportDir = getRunIDReportDir();
		this.addTestResultObserver(new JsonTestResultWriter());
		this.addIgnoredTestObserver(new JsonIgnoredTestWriter());
		this.addIssueObserver(new JsonIssueWriter());
		this.addEventObserver(new JsonEventWriter());
		this.addFixtureResultObserver(new JsonFixtureResultWriter());
	}
	
	@Override
	public synchronized void addTestResultObserver(InternlReportableObserver<TestResult> observer) throws Exception {
		observer.setUp();
		testResultObservers.add(observer);
	}
	
	@Override
	public synchronized void addIgnoredTestObserver(InternlReportableObserver<IgnoredTest> observer) throws Exception {
		observer.setUp();
		ignoredTestObservers.add(observer);
	}

	@Override
	public synchronized void addIssueObserver(InternlReportableObserver<Issue> observer) throws Exception {
		observer.setUp();
		issueObservers.add(observer);
	}

	@Override
	public synchronized void addEventObserver(InternlReportableObserver<Event> observer) throws Exception {
		observer.setUp();
		eventObservers.add(observer);
	}
	
	@Override
	public synchronized void addFixtureResultObserver(InternlReportableObserver<FixtureResult> observer) throws Exception {
		observer.setUp();
		fixtureResultObservers.add(observer);
	}
	
	public synchronized void update(TestResult reportable) throws Exception{
		for (InternlReportableObserver<TestResult> observer: this.testResultObservers) {
			observer.update(reportable);
		}
	}
	
	public synchronized void update(Issue reportable) throws Exception{
		for (InternlReportableObserver<Issue> observer: this.issueObservers) {
			observer.update(reportable);
		}		
	}
	
	public synchronized void update(IgnoredTest reportable) throws Exception{
		for (InternlReportableObserver<IgnoredTest> observer: this.ignoredTestObservers) {
			observer.update(reportable);
		}		
	}
	
	public synchronized void update(Event reportable) throws Exception{
		for (InternlReportableObserver<Event> observer: this.eventObservers) {
			observer.update(reportable);
		}		
	}
	
	public synchronized void update(FixtureResult reportable) throws Exception{
		for (InternlReportableObserver<FixtureResult> observer: this.fixtureResultObservers) {
			observer.update(reportable);
		}		
	}

	/* (non-Javadoc)
	 * @see com.autocognite.dev.testreporter.lib.Reporter#tearDown()
	 */
	@Override
	public void tearDown() throws Exception {
		logger.debug("Tear Down - Begin");
		logger.debug("Tear Down - All Notifiers");
		for (InternlReportableObserver<TestResult> observer: this.testResultObservers) {
			observer.tearDown();
		}
		
		for (InternlReportableObserver<StepResult> observer: this.stepResultObservers) {
			observer.tearDown();
		}
		
		for (InternlReportableObserver<Issue> observer: this.issueObservers) {
			observer.tearDown();
		}
		
		
		for (InternlReportableObserver<FixtureResult> observer: this.fixtureResultObservers) {
			observer.tearDown();
		}
		
		for (InternlReportableObserver<Event> observer: this.eventObservers) {
			observer.tearDown();
		}
		logger.debug("Tear Down - Finish");
	}

	/* (non-Javadoc)
	 * @see com.autocognite.dev.testreporter.lib.Reporter#setUp()
	 */
	@Override
	public void setUp() {
		// TODO Auto-generated method stub

	}
}
