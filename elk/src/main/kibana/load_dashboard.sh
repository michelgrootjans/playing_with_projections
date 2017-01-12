#!/bin/bash
elasticdump --input=quiz-dashboard.json --output=http://localhost:9200/.kibana --type=data
