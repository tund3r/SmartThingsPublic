/**
 *  Back On Schedule!
 *
 *  Copyright 2017 Andrea Dalle Molle
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Back On Schedule!",
    namespace: "tund3r",
    author: "Andrea Dalle Molle",
    description: "Resume Thermostat Schedule On Mode Change",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	
    section("Enable / Disable the following functionality:") {
        input "app_enabled", "bool", title: "Resume Schedule", required:true, defaultValue:true
	}
	section("Select the thermostat to control...") {
		input "thermostat", "capability.thermostat", title: "Thermostat", multiple: true, required: true
	}
    section("Select the modes ...") {
    input "modes", "mode", title: "select a mode(s)", multiple: true
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.
    subscribe(location, "mode", modeChangeHandler)
}

// TODO: implement event handlers

def modeChangeHandler (evt){
   log.debug "state.modeso ${modes}"
   log.debug "mode changed to ${evt.value}"
   if (modes.find{it == evt.value}) {
            log.info "Tried to change to mode '${evt.value}'"
            thermostat.resumeProgram()
            //setLocationMode(newMode)
        }  else {
            log.info "Tried to change to undefined mode '${evt.value}'"
        }
}