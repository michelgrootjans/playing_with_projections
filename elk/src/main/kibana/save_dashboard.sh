#!/bin/bash
elasticdump --input=http://localhost:9200/.kibana --output=$ --type=data --searchBody='{"filter": { "or": [ {"type": {"value": "dashboard"}}, {"type" : {"value":"visualization"}}] }}' > quiz-dashboard.json
