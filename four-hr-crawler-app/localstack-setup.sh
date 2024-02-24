#!/bin/sh

echo "Setting up Localstack DynamoDB"

echo "Creating Crawl State DynamoDB Table"
awslocal dynamodb create-table \
  --table-name crawl_state \
  --attribute-definitions \
    AttributeName=RequestId,AttributeType=S \
  --key-schema \
    AttributeName=RequestId,KeyType=HASH \
  --billing-mode PAY_PER_REQUEST \
  --region us-east-1

awslocal dynamodb update-time-to-live \
  --table-name crawl_state \
  --time-to-live-specification "Enabled=true, AttributeName=TTL"

echo "Creating Crawl Result DynamoDB Table"
awslocal dynamodb create-table \
  --table-name crawl_result \
  --attribute-definitions \
    AttributeName=RequestId,AttributeType=S \
    AttributeName=ParentUrl,AttributeType=S \
  --key-schema \
    AttributeName=RequestId,KeyType=HASH \
    AttributeName=ParentUrl,KeyType=RANGE \
  --billing-mode PAY_PER_REQUEST \
  --region us-east-1

  awslocal dynamodb update-time-to-live \
    --table-name crawl_result \
    --time-to-live-specification "Enabled=true, AttributeName=TTL"
