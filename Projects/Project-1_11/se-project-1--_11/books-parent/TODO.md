TODO
====

- Rescale cover to 192px width max if necessary (server)
- Rating and memo (text + images) (client/server)
- Links to Amazon, DuckDuckGo search, Google search

- Api key: POST /app to modify it, warning if no key at all (client)
- FB Open Graph integration (client/server)

Facebook Graph API
------------------

https://developers.facebook.com/tools/explorer?method=GET&path=book.reads%3Fids%3D1036202288

me/friends?fields=first_name,last_name,id to fetch friends
book.reads?ids=1036202288,...&fields=data to fetch friends' books
?ids=10150805569322417,10150684831643097&fields=data to fetch books' ISBN