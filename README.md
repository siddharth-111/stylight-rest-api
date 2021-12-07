# Trade Republic Coding Challenge

## Content
- Assumptions
- Design approach
- Tech stack
- Future Development Discussion

## Assumptions

- End user only wants candlesticks for last 30 minutes and any data older than that might not be required.
- As the end user wants only candlesticks, purging quotes post creation of candlesticks is acceptable considering the volume of quotes is quite high.
- As soon as an Isin "delete" event is received, the associated quotes and candlestick data older than deletion timestamp of Isin is considered invalid

## Design approach
Based on the assumptions, the design revolves around scheduling tasks at intervals to create candlesticks.

There are 3 entities: Instruments, Quotes and Candlesticks.

There are 3 schedulers:
- Candlestick scheduler - which runs every minute and creates candlestick for instruments which have quotes for that minute.


- Quotes scheduler - Deletes quotes every two minutes. As the candlestick scheduler has already created candlestick in the window of one minute, we do not require quotes older than a minute. It's scheduled for 2 minutes for a safer window.


- Candlestick scheduler - Deletes candlesticks older than an hour. As the user only expects candlesticks newer than 30 minutes, candlesticks older than that are not relevant. It's set for 1 hour for a safer window.


### Tech stack
- Spring boot
- MySQL

## Future Development Discussion

### Design improvement/Optimization

- The current scheduler to create candlesticks can be made async for better performance.
- The logic to fill empty slots of candlesticks while fetching candlesticks to client can be revised.